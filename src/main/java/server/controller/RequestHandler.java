package server.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import model.UserRequest;
import model.enums.Request;
import org.jetbrains.annotations.NotNull;
import server.model.Command;
import server.service.AuthenticationService;
import server.service.LoginCommand;
import server.service.LogoutCommand;
import server.service.RegisterCommand;

/**
 * Handles a request from a client.
 */
public class RequestHandler implements Runnable, AutoCloseable {

    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private final Map<Request, Command> commandMap = new HashMap<>();


    /**
     * Constructor for RequestHandler.
     *
     * @param socket the socket to accept
     */
    public RequestHandler(@NotNull Socket socket) throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
        final AuthenticationService authenticationService = new AuthenticationService();
        this.commandMap.put(Request.LOGIN, new LoginCommand(authenticationService, out));
        this.commandMap.put(Request.LOGOUT, new LogoutCommand(authenticationService, out));
        this.commandMap.put(Request.REGISTER, new RegisterCommand(authenticationService, out));
    }

    @Override
    public void run() {

        while (true) {
            UserRequest userRequest;
            try {
                userRequest = (UserRequest) in.readObject();
                final Command command = commandMap.get(userRequest.request());
                if (command == null) {
                    throw new IllegalStateException("Command not found.");
                }
                if (!command.handle(userRequest)) {
                    break;
                }
            } catch (IOException | ClassNotFoundException e) {
                break;
            }
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
