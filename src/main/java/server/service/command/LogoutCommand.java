package server.service.command;

import model.Request;
import model.Response;
import model.enums.RequestType;
import model.enums.Status;
import org.jetbrains.annotations.NotNull;
import server.model.Command;
import server.service.AuthenticationService;

/**
 * Handles a logout request.
 */
public class LogoutCommand implements Command {

    private final AuthenticationService authenticationService;

    /**
     * Constructor for LogoutCommand.
     *
     * @param authenticationService the authentication service
     */
    public LogoutCommand(@NotNull AuthenticationService authenticationService) {
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
            throw new IllegalArgumentException("Cannot handle a non-LOGIN requestType");
        } else if (request.data() == null) {
            throw new IllegalArgumentException("Cannot logout a user with null username");
        }

        return authenticationService.logout()
                ? new Response(Status.SUCCESS, "Logout successful.")
                : new Response(Status.FAILURE, "User not logged in.");

    }

}
