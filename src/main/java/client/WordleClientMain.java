package client;

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
import model.enums.RequestType;

/**
 * WordleClientMain is the client for the Wordle game.
 * It is responsible for connecting to the server and
 * sending and receiving messages.
 */
public class WordleClientMain {

    private static Socket socket;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;

    private static final Response errorResponse =
            new Response(-1, "Error sending requestType to server.");


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
     * Sends a login requestType to the server.
     *
     * @param username the username of the user sending the requestType
     * @param password the password of the user sending the requestType
     */
    public static Optional<Response> login(String username, String password) {
        final String hashedPassword = PasswordHashingService.getInstance().hashPassword(password);
        final Request request = new Request(RequestType.LOGIN, username, hashedPassword);
        if (StreamHandler.sendData(out, request)) {
            return StreamHandler.getData(in, Response.class);
        }
        return Optional.of(errorResponse);
    }

    /**
     * Sends a register requestType to the server.
     *
     * @param username the username of the user sending the requestType
     * @param password the password of the user sending the requestType
     */
    public static Optional<Response> register(String username, String password) {
        final String hashedPassword = PasswordHashingService.getInstance().hashPassword(password);
        final Request request = new Request(RequestType.REGISTER, username, hashedPassword);
        if (StreamHandler.sendData(out, request)) {
            return StreamHandler.getData(in, Response.class);
        }
        return Optional.of(errorResponse);
    }

    /**
     * Sends a logout requestType to the server.
     *
     * @param username the username of the user sending the requestType
     */
    public static Optional<Response> logout(String username) {
        final Request request = new Request(RequestType.LOGOUT, username);
        if (StreamHandler.sendData(out, request)) {
            return StreamHandler.getData(in, Response.class);
        }
        return Optional.of(errorResponse);
    }

    /**
     * Sends a play requestType to the server.
     *
     * @param username the username of the user sending the requestType
     */
    public static Optional<Response> play(String username) {
        final Request request = new Request(RequestType.PLAY, username);
        if (StreamHandler.sendData(out, request)) {
            return StreamHandler.getData(in, Response.class);
        }
        return Optional.of(errorResponse);
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
        if (StreamHandler.sendData(out, request)) {
            return StreamHandler.getData(in, Response.class);
        }
        return Optional.of(errorResponse);
    }

}