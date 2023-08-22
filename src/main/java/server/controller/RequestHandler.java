package server.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import model.Request;
import model.Response;
import model.StreamHandler;
import model.enums.RequestType;
import org.jetbrains.annotations.NotNull;
import server.model.Command;
import server.service.AuthenticationService;
import server.service.PlayWordleService;
import server.service.UserStatisticsService;
import server.service.command.LoginCommand;
import server.service.command.LogoutCommand;
import server.service.command.PlayCommand;
import server.service.command.RegisterCommand;
import server.service.command.SendWordCommand;

/**
 * Handles a requestType from a client.
 */
public class RequestHandler implements Runnable, AutoCloseable {

    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private static final Map<RequestType, Command> commandMap;

    static {
        commandMap = new HashMap<>();
        final AuthenticationService authenticationService = new AuthenticationService();
        final UserStatisticsService userStatisticsService = new UserStatisticsService();
        final PlayWordleService playWordleService = new PlayWordleService();
        commandMap.put(RequestType.LOGIN, new LoginCommand(authenticationService));
        commandMap.put(RequestType.LOGOUT, new LogoutCommand(authenticationService));
        commandMap.put(RequestType.REGISTER, new RegisterCommand(authenticationService));
        commandMap.put(RequestType.PLAY, new PlayCommand(playWordleService, userStatisticsService));
        commandMap.put(
                RequestType.SENDWORD,
                new SendWordCommand(playWordleService, userStatisticsService));
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
            final Optional<Request> userRequest = StreamHandler.getData(in, Request.class);
            if (userRequest.isEmpty()) {
                break;
            }

            final Command command = commandMap.get(userRequest.get().requestType());
            if (command == null) {
                throw new IllegalStateException("Command not found.");
            }

            final Response response = command.handle(userRequest.get());

            if (!StreamHandler.sendData(out, response)) {
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
