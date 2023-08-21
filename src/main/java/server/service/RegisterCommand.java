package server.service;

import model.Response;
import model.User;
import model.UserRequest;
import model.enums.RequestType;
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
     * Handles a user requestType.
     *
     * @param userRequest the user requestType
     * @return the server response
     */
    public Response handle(@NotNull UserRequest userRequest) {

        if (userRequest.requestType() != RequestType.REGISTER) {
            throw new IllegalArgumentException("Cannot handle a non-register requestType");
        } else if (userRequest.user() == null) {
            throw new IllegalArgumentException("Cannot register a null user");
        }

        final User user = userRequest.user();

        if (user.getPasswordHash().isEmpty()) {
            return new Response(-1, "Password cannot be empty.");
        } else if (authenticationService.getUserByUsername(user.getUsername()).isPresent()) {
            return new Response(-1, "User already registered.");
        } else if (authenticationService.add(user)) {
            return new Response(0, "Registration successful.");
        }
        return new Response(-1, "Registration failed.");

    }
}
