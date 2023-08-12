package server.service;

import model.ServerResponse;
import model.User;
import model.UserRequest;
import org.jetbrains.annotations.NotNull;
import server.model.Command;

/**
 * Register command.
 */
public class RegisterCommand implements Command {

    private final AuthenticationService authenticationService;

    /**
     * Constructor for RegisterCommand.
     *
     * @param authenticationService the authentication service
     */
    public RegisterCommand(AuthenticationService authenticationService) {
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

        if (user.getPasswordHash().isEmpty()) {
            return new ServerResponse(-1, "Password cannot be empty.");
        } else if (authenticationService.getUserByUsername(user.getUsername()).isPresent()) {
            return new ServerResponse(-1, "User already registered.");
        } else if (authenticationService.add(user)) {
            return new ServerResponse(0, "Registration successful.");
        }
        return new ServerResponse(-1, "Registration failed.");

    }
}
