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
     * @param request the user requestType
     * @return the server response
     */
    public Response handle(@NotNull Request request) {

        if (request.requestType() != RequestType.REGISTER) {
            throw new IllegalArgumentException("Cannot handle a non-register requestType");
        } else if (request.username() == null) {
            throw new IllegalArgumentException("Cannot register a null user");
        }

        final User user = new User(request.username(), (String) request.data());

        if (user.getPasswordHash().isEmpty()) {
            return new Response(Status.FAILURE, "Password cannot be empty.");
        } else if (authenticationService.getUserByUsername(user.getUsername()).isPresent()) {
            return new Response(Status.FAILURE, "User already registered.");
        } else if (authenticationService.add(user)) {
            return new Response(Status.SUCCESS, "Registration successful.");
        }
        return new Response(Status.FAILURE, "Registration failed.");

    }
}
