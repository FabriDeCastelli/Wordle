package client;

import client.gui.authentication.AuthenticationPage;
import client.service.AuthenticationService;
import client.service.NotificationService;
import client.service.PasswordHashingService;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import model.AuthDTO;
import model.Request;
import model.Response;
import model.StreamHandler;
import model.WordAttempt;
import model.WordHints;
import model.enums.AuthType;
import model.enums.RequestType;
import model.enums.Status;
import org.jetbrains.annotations.NotNull;

/**
 * WordleClientMain is the client for the Wordle game.
 * It is responsible for connecting to the server and
 * sending and receiving messages.
 */
public class WordleClientMain {

    private static Socket socket;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;
    private static String multicastIP;
    private static int multicastPort;

    static {
        final int port;
        final String server;
        try (final InputStream inputStream =
                     new FileInputStream("src/main/java/client/conf/client.properties")) {
            final Properties properties = new Properties();
            properties.load(inputStream);
            server = properties.getProperty("SERVER");
            port = Integer.parseInt(properties.getProperty("PORT"));
            multicastIP = properties.getProperty("MULTICAST_IP");
            multicastPort = Integer.parseInt(properties.getProperty("MULTICAST_PORT"));
            socket = new Socket(server, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Error connecting to server.");
        }
    }


    /**
     * Main method for the WordleClientMain.
     */
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down...");
            AuthenticationService.getInstance().getLoggedUser().ifPresent(user -> {
                final Optional<Response> response = WordleClientMain.logout();
                response.ifPresent(res -> System.out.println(res.message()));
            });
            WordleClientMain.closeResources();
        }));
        new AuthenticationPage().setVisible(true);
    }

    /**
     * Sends a LOGIN or REGISTER request to the server.
     *
     * @param authType the type of authentication
     * @param username the username
     * @param password the password
     * @return the response from the server
     */
    public static Optional<Response> authenticate(
            @NotNull AuthType authType,
            @NotNull String username,
            @NotNull String password) {
        final String hashedPassword = PasswordHashingService.getInstance().hashPassword(password);
        final AuthDTO authDTO = new AuthDTO(username, hashedPassword);
        final Request request = new Request(RequestType.valueOf(authType.name()), authDTO);
        if (StreamHandler.sendData(out, request)) {
            final Optional<Response> response = StreamHandler.getData(in, Response.class);
            response.filter(res -> res.status() == Status.SUCCESS)
                    .ifPresent(res -> AuthenticationService.getInstance().login(username));
            new NotificationService(username, multicastIP, multicastPort).start();
            return response;
        }
        return Optional.empty();
    }

    /**
     * Sends a LOGOUT request to the server.
     *
     * @return the response from the server
     */
    public static Optional<Response> logout() {
        final Request request = new Request(RequestType.LOGOUT);
        if (StreamHandler.sendData(out, request)) {
            final Optional<Response> response = StreamHandler.getData(in, Response.class);
            response.filter(res -> res.status() == Status.SUCCESS)
                    .ifPresent(res -> AuthenticationService.getInstance().logout());
            return response;
        }
        return Optional.empty();
    }

    /**
     * Sends a CHANGE PASSWORD request to the server.
     *
     * @param newPassword the new password
     * @return the response from the server
     */
    public static Optional<Response> changePassword(String newPassword) {
        final String newPasswordHash =
                PasswordHashingService.getInstance().hashPassword(newPassword);
        final Request request = new Request(RequestType.CHANGEPASSWORD, newPasswordHash);
        return StreamHandler.sendAndGetResponse(out, in, request);
    }

    /**
     * Sends a PLAY request to the server.
     *
     * @return the response from the server
     */
    public static Optional<Response> play() {
        final Request request = new Request(RequestType.PLAY);
        return StreamHandler.sendAndGetResponse(out, in, request);
    }

    /**
     * Sends a SENDWORD to the server.
     *
     * @param word the word to be sent
     * @return the response from the server
     */
    public static Optional<Response> sendWord(String word, int attemptNumber) {
        final Request request = new Request(
                RequestType.SENDWORD, new WordAttempt(word, attemptNumber));
        return StreamHandler.sendAndGetResponse(out, in, request);
    }

    /**
     * Sends a share request to the server.
     *
     * @param wordHintsHistory the history of the game the user has played
     * @return the response from the server
     */
    public static Optional<Response> share(List<WordHints> wordHintsHistory) {
        final Request request = new Request(RequestType.SHARE, wordHintsHistory);
        return StreamHandler.sendAndGetResponse(out, in, request);
    }

    /**
     * Sends a SENDMESTATISTICS request to the server.
     *
     * @return the response from the server
     */
    public static Optional<Response> sendMeStatistics() {
        final Request request = new Request(RequestType.SENDMESTATISTICS);
        return StreamHandler.sendAndGetResponse(out, in, request);
    }

    /**
     * Retrieves all the notifications received so far.
     *
     * @return the response with all notifications
     */
    public static Optional<Response> showMeSharing() {
        return Optional.of(
                new Response(
                        Status.SUCCESS, "Success",
                        NotificationService.notifications));
    }

    /**
     * Closes the resources used by the client.
     */
    public static void closeResources() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Client: error closing resources.");
        }
    }

}