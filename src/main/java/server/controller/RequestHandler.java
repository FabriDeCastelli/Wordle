package server.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import model.ServerResponse;
import model.StreamHandler;
import model.UserRequest;
import model.enums.Request;
import org.jetbrains.annotations.NotNull;
import server.model.Command;
import server.service.AuthenticationService;
import server.service.LoginCommand;
import server.service.LogoutCommand;
import server.service.PlayCommand;
import server.service.PlayWordleService;
import server.service.RegisterCommand;

/**
 * Handles a request from a client.
 */
public class RequestHandler implements Runnable, AutoCloseable {

    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private static final Map<Request, Command> commandMap;

    static {
        commandMap = new HashMap<>();
        final AuthenticationService authenticationService = new AuthenticationService();
        commandMap.put(Request.LOGIN, new LoginCommand(authenticationService));
        commandMap.put(Request.LOGOUT, new LogoutCommand(authenticationService));
        commandMap.put(Request.REGISTER, new RegisterCommand(authenticationService));
        commandMap.put(Request.PLAY, new PlayCommand(new PlayWordleService()));
    }


    /**
     * Constructor for RequestHandler.
     *
     * @param socket the socket to accept
     */
    public RequestHandler(@NotNull Socket socket) throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {

        while (true) {
            final Optional<UserRequest> userRequest = StreamHandler.getData(in, UserRequest.class);
            if (userRequest.isEmpty()) {
                break;
            }

            final Command command = commandMap.get(userRequest.get().request());
            if (command == null) {
                throw new IllegalStateException("Command not found.");
            }

            final ServerResponse serverResponse = command.handle(userRequest.get());

            if (!StreamHandler.sendData(out, serverResponse)) {
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
