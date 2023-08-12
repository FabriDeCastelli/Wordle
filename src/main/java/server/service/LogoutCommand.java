package server.service;

import java.io.ObjectOutputStream;
import model.ServerResponse;
import model.User;
import model.UserRequest;
import org.jetbrains.annotations.NotNull;
import server.model.Command;

/**
 * Logout command.
 */
public class LogoutCommand implements Command {

    private final AuthenticationService authenticationService;

    /**
     * Constructor for LogoutCommand.
     *
     * @param authenticationService the authentication service
     */
    public LogoutCommand(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Handles a user request.
     *
     * @param userRequest the user request
     * @return the server response
     */
    public ServerResponse handle(@NotNull UserRequest userRequest) {

        final User user = userRequest.user();
        if (authenticationService.getUserByUsername(user.getUsername()).isEmpty()) {
            throw new IllegalStateException("Cannot logout a not registered user");
        }
        return new ServerResponse(0, "Logout successful.");
    }

}
