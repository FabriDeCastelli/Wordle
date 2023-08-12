package server.service;

import java.util.Optional;
import model.ServerResponse;
import model.User;
import model.UserRequest;
import model.enums.Request;
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
     * Handles a user request.
     *
     * @param userRequest the user request
     * @return the server response
     */
    public ServerResponse handle(@NotNull UserRequest userRequest) {

        if (userRequest.request() != Request.LOGIN) {
            throw new IllegalArgumentException("Cannot handle a non-logout request");
        } else if (userRequest.user() == null) {
            throw new IllegalArgumentException("Cannot logout a null user");
        }

        final User user = userRequest.user();
        final Optional<User> isRegistered =
                authenticationService.getUserByUsername(user.getUsername());

        if (isRegistered.isEmpty()) {
            return new ServerResponse(-1, "User not registered.");
        } else if (isRegistered.get().equals(user)) {
            return new ServerResponse(0, "Login successful.");
        }
        return new ServerResponse(-1, "Wrong username or password.");

    }

}

