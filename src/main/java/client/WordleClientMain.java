package client;

import client.controller.NotificationController;
import client.gui.AuthenticationPage;
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
import model.Request;
import model.Response;
import model.StreamHandler;
import model.WordAttempt;
import model.WordHints;
import model.enums.RequestType;
import model.enums.Status;

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


    /**
     * Main method for the WordleClientMain.
     */
    public static void main(String[] args) {

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
            return;
        }
        new AuthenticationPage().setVisible(true);

    }


    /**
     * Sends a login requestType to the server.
     *
     * @param username the username of the user sending the requestType
     * @param password the password of the user sending the requestType
     * @return the response from the server
     */
    public static Optional<Response> login(String username, String password) {
        final String hashedPassword = PasswordHashingService.getInstance().hashPassword(password);
        final Request request = new Request(RequestType.LOGIN, username, hashedPassword);
        if (StreamHandler.sendData(out, request)) {
            new NotificationController(username, multicastIP, multicastPort).start();
            return StreamHandler.getData(in, Response.class);
        }
        return Optional.empty();
    }

    /**
     * Sends a register requestType to the server.
     *
     * @param username the username of the user sending the request
     * @param password the password of the user sending the request
     * @return the response from the server
     */
    public static Optional<Response> register(String username, String password) {
        final String hashedPassword = PasswordHashingService.getInstance().hashPassword(password);
        final Request request = new Request(RequestType.REGISTER, username, hashedPassword);
        return StreamHandler.sendData(out, request)
                ? StreamHandler.getData(in, Response.class)
                : Optional.empty();
    }

    /**
     * Sends a logout requestType to the server.
     *
     * @param username the username of the user sending the requestType
     * @return the response from the server
     */
    public static Optional<Response> logout(String username) {
        final Request request = new Request(RequestType.LOGOUT, username);
        return StreamHandler.sendData(out, request)
                ? StreamHandler.getData(in, Response.class)
                : Optional.empty();
    }

    /**
     * Sends a play requestType to the server.
     *
     * @param username the username of the user sending the requestType
     * @return the response from the server
     */
    public static Optional<Response> play(String username) {
        final Request request = new Request(RequestType.PLAY, username);
        return StreamHandler.sendData(out, request)
                ? StreamHandler.getData(in, Response.class)
                : Optional.empty();
    }

    /**
     * Sends a word to the server.
     *
     * @param username the username of the user sending the requestType
     * @param word the word to send
     * @return the response from the server
     */
    public static Optional<Response> sendWord(String username, String word, int attemptNumber) {
        final Request request = new Request(
                RequestType.SENDWORD, username, new WordAttempt(word, attemptNumber));
        return StreamHandler.sendData(out, request)
                ? StreamHandler.getData(in, Response.class)
                : Optional.empty();
    }

    /**
     * Sends a share requestType to the server.
     *
     * @param username the username of the user sending the requestType
     * @param wordHintsHistory the history of the game the user has played
     * @return the response from the server
     */
    public static Optional<Response> share(String username, List<WordHints> wordHintsHistory) {
        final Request request = new Request(RequestType.SHARE, username, wordHintsHistory);
        return StreamHandler.sendData(out, request)
                ? StreamHandler.getData(in, Response.class)
                : Optional.empty();
    }

    /**
     * Sends a showMyStatistics requestType to the server.
     *
     * @param username the username of the user sending the request
     * @return the response from the server
     */
    public static Optional<Response> sendMeStatistics(String username) {
        final Request request = new Request(RequestType.SENDMESTATISTICS, username);
        return StreamHandler.sendData(out, request)
                ? StreamHandler.getData(in, Response.class)
                : Optional.empty();
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
                        NotificationController.notifications));
    }

}