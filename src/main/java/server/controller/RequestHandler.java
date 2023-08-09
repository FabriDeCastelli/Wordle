package server.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;
import model.ServerResponse;
import model.User;
import model.UserRequest;
import org.jetbrains.annotations.NotNull;
import server.service.AuthenticationService;

/**
 * Handles a request from a client.
 */
public class RequestHandler implements Runnable, AutoCloseable {

    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;


    /**
     * Constructor for RequestHandler.
     *
     * @param socket the socket to accept
     */
    public RequestHandler(
            @NotNull Socket socket,
            ObjectInputStream in,
            ObjectOutputStream out) {
        this.socket = socket;
        this.out = out;
        this.in = in;
    }



    @Override
    public void run() {
        while (true) {
            this.handleUserRequest();
        }

    }

    /**
     * Handles a user request.
     */
    public void handleUserRequest() {
        UserRequest userRequest;
        try {
            userRequest = (UserRequest) in.readObject();
            switch (userRequest.request()) {
                case LOGIN -> this.login(userRequest.user());
                case REGISTER -> this.register(userRequest.user());
                case LOGOUT -> this.logout(userRequest.user());
                case ENDSESSION -> this.close();
                default -> throw new IllegalStateException("RequestHandler: Unexpected request.");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles a login request.
     *
     * @param user the user that wants to log in
     */
    public void login(@NotNull User user) {
        final AuthenticationService authenticationService =
                new AuthenticationService();
        final Optional<User> isRegistered =
                authenticationService.getUserByUsername(user.getUsername());
        final ServerResponse response;

        if (isRegistered.isEmpty()) {
            response = new ServerResponse(-1, "User not registered.");
        } else if (isRegistered.get().equals(user)) {
            response = new ServerResponse(0, "Login successful.");
        } else {
            response = new ServerResponse(-1, "Wrong username or password.");
        }

        sendResponse(response);

    }

    /**
     * Handles a register request.
     *
     * @param user the user to register
     */
    public void register(@NotNull User user) {

        // TODO: understand why PMD reports DU anomaly if I initialize the authentication service

        final ServerResponse serverResponse;

        if (user.getPasswordHash().equals("")) {
            serverResponse = new ServerResponse(-1, "Password cannot be empty.");
        } else if (new AuthenticationService().getUserByUsername(user.getUsername()).isPresent()) {
            serverResponse = new ServerResponse(-1, "User already registered.");
        } else {
            serverResponse = new ServerResponse(0, "Registration successful.");
            new AuthenticationService().add(user);
        }

        sendResponse(serverResponse);
    }

    /**
     * Handles a logout request.
     *
     * @param user the user to logout
     */
    public void logout(@NotNull User user) {

        final AuthenticationService authenticationService =
                new AuthenticationService();
        if (authenticationService.getUserByUsername(user.getUsername()).isEmpty()) {
            throw new IllegalStateException("Cannot logout a not registered user");
        }
        sendResponse(new ServerResponse(0, "Logout successful."));
    }

    /**
     * Sends a response to the client.
     *
     * @param serverResponse the server response
     */
    private void sendResponse(@NotNull ServerResponse serverResponse) {
        try {
            out.writeObject(serverResponse);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the socket.
     *
     * @throws IOException if an I/O error occurs when closing this socket.
     */
    @Override
    public void close() throws IOException {
        this.in.close();
        this.out.close();
        this.socket.close();
    }


}
