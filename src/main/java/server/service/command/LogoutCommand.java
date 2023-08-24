package server.service.command;

import model.Request;
import model.Response;
import model.User;
import model.enums.RequestType;
import model.enums.Status;
import org.jetbrains.annotations.NotNull;
import server.model.Command;
import server.service.AuthenticationService;

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
        } else if (request.username() == null) {
            throw new IllegalArgumentException("Cannot logout a user with null username");
        }

        return authenticationService.getLoggedUserByUsername(request.username())
                .filter(authenticationService::removeFromLoggedUsers)
                .map(username -> new Response(Status.SUCCESS, "Logout successful."))
                .orElseGet(() -> new Response(Status.FAILURE, "User not logged in."));
    }

}
