package server.service.command;

import model.AuthDTO;
import model.Request;
import model.Response;
import model.User;
import model.enums.RequestType;
import model.enums.Status;
import org.jetbrains.annotations.NotNull;
import server.model.Command;
import server.service.AuthenticationService;

/**
 * Login command.
 */
public class LoginCommand implements Command {

    private final AuthenticationService authenticationService;

    /**
     * Constructor for LoginCommand.
     *
     * @param authenticationService the authentication service
     */
    public LoginCommand(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Handles a user requestType.
     *
     * @param request the user requestType
     * @return the server response
     */
    public Response handle(@NotNull Request request) {

        if (request.requestType() != RequestType.LOGIN) {
            throw new IllegalArgumentException("Cannot handle a non-LOGIN request");
        }

        final User user = new User((AuthDTO) request.data());

        return authenticationService.getRegisteredUserByUsername(user.getUsername())
                .filter(u -> u.getPasswordHash().equals(user.getPasswordHash())
                        && !user.getUsername().isEmpty())
                .map(u -> authenticationService.login(user)
                ? new Response(Status.SUCCESS, "Login successful.")
                : new Response(Status.FAILURE, "User already logged in.")
                ).orElseGet(() -> new Response(Status.FAILURE, "Wrong username or password."));

    }

}

