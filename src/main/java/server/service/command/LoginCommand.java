package server.service.command;

import java.util.Optional;
import model.Request;
import model.Response;
import model.User;
import model.enums.RequestType;
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
            throw new IllegalArgumentException("Cannot handle a non-logout requestType");
        } else if (request.username() == null) {
            throw new IllegalArgumentException("Cannot logout a null user");
        }

        return authenticationService.getUserByUsername(request.username())
                .map(value -> value.equals(new User(request.username(), (String) request.data()))
                ? new Response(0, "Login successful.")
                : new Response(-1, "Wrong username or password.")
                ).orElseGet(() -> new Response(-1, "User not registered."));

    }

}

