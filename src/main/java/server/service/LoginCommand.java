package server.service;

import java.util.Optional;
import model.Response;
import model.User;
import model.UserRequest;
import model.enums.RequestType;
import org.jetbrains.annotations.NotNull;
import server.model.Command;

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
     * @param userRequest the user requestType
     * @return the server response
     */
    public Response handle(@NotNull UserRequest userRequest) {

        if (userRequest.requestType() != RequestType.LOGIN) {
            throw new IllegalArgumentException("Cannot handle a non-logout requestType");
        } else if (userRequest.user() == null) {
            throw new IllegalArgumentException("Cannot logout a null user");
        }

        final User user = userRequest.user();
        final Optional<User> isRegistered =
                authenticationService.getUserByUsername(user.getUsername());

        if (isRegistered.isEmpty()) {
            return new Response(-1, "User not registered.");
        } else if (isRegistered.get().equals(user)) {
            return new Response(0, "Login successful.");
        }
        return new Response(-1, "Wrong username or password.");

    }

}

