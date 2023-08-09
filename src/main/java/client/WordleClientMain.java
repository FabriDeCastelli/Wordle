package client;

import client.gui.AuthenticationPage;
import client.service.PasswordHashingService;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;
import model.ServerResponse;
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
            e.printStackTrace();
            return;
        }

        new AuthenticationPage().setVisible(true);


    }

    /**
     * Sends a request to the server.
     *
     * @param userRequest the user request
     */
    public static void sendRequest(UserRequest userRequest) {
        try {
            out.writeObject(userRequest);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a response from the server.
     *
     * @return the response
     */
    public static ServerResponse getResponse() {
        try {
            return (ServerResponse) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ServerResponse(-1, "Error reading response from server.");
    }

    /**
     * Sends a login request to the server.
     *
     * @param username the getUsername
     * @param password the password
     */
    public static ServerResponse login(String username, String password) {
        final PasswordHashingService passwordHashingService = PasswordHashingService.getInstance();
        final String hashedPassword = passwordHashingService.hashPassword(password);
        final User user = new User(username, hashedPassword);
        final UserRequest userRequest = new UserRequest(Request.LOGIN, user);
        sendRequest(userRequest);
        return getResponse();
    }

    /**
     * Sends a register request to the server.
     *
     * @param username the getUsername
     * @param password the password
     */
    public static ServerResponse register(String username, String password) {
        final PasswordHashingService passwordHashingService = PasswordHashingService.getInstance();
        final String hashedPassword = passwordHashingService.hashPassword(password);
        final User user = new User(username, hashedPassword);
        final UserRequest userRequest = new UserRequest(Request.REGISTER, user);
        sendRequest(userRequest);
        return getResponse();
    }

    /**
     * Sends a logout request to the server.
     *
     * @param username the username
     */
    public static ServerResponse logout(String username) {
        final User user = new User(username, "");
        final UserRequest userRequest = new UserRequest(Request.LOGOUT, user);
        sendRequest(userRequest);
        return getResponse();
    }

}