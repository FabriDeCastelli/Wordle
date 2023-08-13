package client;

import client.gui.AuthenticationPage;
import client.service.PasswordHashingService;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;
import java.util.Properties;
import model.ServerResponse;
import model.StreamHandler;
import model.User;
import model.UserRequest;
import model.enums.Request;

/**
 * WordleClientMain is the client for the Wordle game.
 * It is responsible for connecting to the server and
 * sending and receiving messages.
 */
public class WordleClientMain {

    private static Socket socket;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;

    private static final ServerResponse errorResponse =
            new ServerResponse(-1, "Error sending request to server.");


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
     * Sends a login request to the server.
     *
     * @param username the username of the user sending the request
     * @param password the password of the user sending the request
     */
    public static Optional<ServerResponse> login(String username, String password) {
        final PasswordHashingService passwordHashingService = PasswordHashingService.getInstance();
        final String hashedPassword = passwordHashingService.hashPassword(password);
        final User user = new User(username, hashedPassword);
        final UserRequest userRequest = new UserRequest(Request.LOGIN, user);
        if (StreamHandler.sendData(out, userRequest)) {
            return StreamHandler.getData(in, ServerResponse.class);
        }
        return Optional.of(errorResponse);
    }

    /**
     * Sends a register request to the server.
     *
     * @param username the username of the user sending the request
     * @param password the password of the user sending the request
     */
    public static Optional<ServerResponse> register(String username, String password) {
        final PasswordHashingService passwordHashingService = PasswordHashingService.getInstance();
        final String hashedPassword = passwordHashingService.hashPassword(password);
        final User user = new User(username, hashedPassword);
        final UserRequest userRequest = new UserRequest(Request.REGISTER, user);
        if (StreamHandler.sendData(out, userRequest)) {
            return StreamHandler.getData(in, ServerResponse.class);
        }
        return Optional.of(errorResponse);
    }

    /**
     * Sends a logout request to the server.
     *
     * @param username the username of the user sending the request
     */
    public static Optional<ServerResponse> logout(String username) {
        final User user = new User(username, "");
        final UserRequest userRequest = new UserRequest(Request.LOGOUT, user);
        if (StreamHandler.sendData(out, userRequest)) {
            return StreamHandler.getData(in, ServerResponse.class);
        }
        return Optional.of(errorResponse);
    }

    /**
     * Sends a play request to the server.
     *
     * @param username the username of the user sending the request
     */
    public static Optional<ServerResponse> play(String username) {
        final User user = new User(username, "");
        final UserRequest userRequest = new UserRequest(Request.PLAY, user);
        if (StreamHandler.sendData(out, userRequest)) {
            return StreamHandler.getData(in, ServerResponse.class);
        }
        return Optional.of(errorResponse);
    }

}