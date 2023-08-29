package server.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MulticastSocket;
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
import server.service.command.SendMeStatisticsCommand;
import server.service.command.SendWordCommand;
import server.service.command.ShareCommand;

/**
 * Handles a request from a client.
 */
public class RequestHandler implements Runnable, AutoCloseable {

    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private final MulticastSocket multicastSocket;
    private final Map<RequestType, Command> commandMap;


    /**
     * Constructor for RequestHandler.
     *
     * @param socket          the socket of the server, already accepted
     * @param multicastSocket the multicast socket on which to send notifications
     */
    public RequestHandler(@NotNull Socket socket, @NotNull MulticastSocket multicastSocket)
            throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
        this.multicastSocket = multicastSocket;
        this.commandMap = new HashMap<>();
        final AuthenticationService authenticationService = new AuthenticationService();
        final UserStatisticsService userStatisticsService = new UserStatisticsService();
        final PlayWordleService playWordleService = new PlayWordleService();
        commandMap.put(RequestType.LOGIN,
                new LoginCommand(authenticationService));
        commandMap.put(RequestType.LOGOUT,
                new LogoutCommand(authenticationService));
        commandMap.put(RequestType.REGISTER,
                new RegisterCommand(authenticationService));
        commandMap.put(RequestType.PLAY,
                new PlayCommand(playWordleService, userStatisticsService, authenticationService));
        commandMap.put(RequestType.SENDWORD, new SendWordCommand(
                playWordleService, userStatisticsService, authenticationService));
        commandMap.put(RequestType.SENDMESTATISTICS,
                new SendMeStatisticsCommand(userStatisticsService, authenticationService));
        commandMap.put(RequestType.SHARE,
                new ShareCommand(multicastSocket, authenticationService));
    }

    @Override
    public void run() {

        while (true) {
            final Optional<Request> request = StreamHandler.getData(in, Request.class);
            if (request.isEmpty()) {
                break;
            }

            final RequestType requestType = request.get().requestType();

            final Command command = commandMap.get(requestType);

            if (command == null) {
                throw new IllegalStateException("Command not found.");
            }

            final Response response = command.handle(request.get());

            if (!StreamHandler.sendData(out, response)) {
                break;
            }
        }

    }

    /**
     * Closes resources.
     *
     * @throws IOException if an I/O error occurs when closing this socket.
     */
    @Override
    public void close() throws IOException {
        this.in.close();
        this.out.close();
        this.socket.close();
        this.multicastSocket.close();
    }


}
