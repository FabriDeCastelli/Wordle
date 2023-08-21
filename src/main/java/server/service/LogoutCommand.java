package server.service;

import model.Request;
import model.Response;
import model.User;
import model.enums.RequestType;
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
     * Handles a user requestType.
     *
     * @param request the user requestType
     * @return the server response
     */
    public Response handle(@NotNull Request request) {

        if (request.requestType() != RequestType.LOGOUT) {
            throw new IllegalArgumentException("Cannot handle a non-logout requestType");
        } else if (request.user() == null) {
            throw new IllegalArgumentException("Cannot logout a null user");
        }

        final User user = request.user();
        if (authenticationService.getUserByUsername(user.getUsername()).isEmpty()) {
            throw new IllegalStateException("Cannot logout a not registered user");
        }
        return new Response(0, "Logout successful.");
    }

}
