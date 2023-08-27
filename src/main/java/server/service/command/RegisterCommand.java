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
            throw new IllegalArgumentException("Cannot handle a non-REGISTER requestType");
        } else if (request.data() == null) {
            throw new IllegalArgumentException("Cannot register a null AuthDTO");
        }

        final User user = new User((AuthDTO) request.data());

        return user.getPasswordHash().isEmpty()
                ? new Response(Status.FAILURE, "Username or password cannot be empty.")
                : authenticationService.register(user)
                ? new Response(Status.SUCCESS, "Registration successful.")
                : new Response(Status.FAILURE, "User already registered.");

    }
}
