package server.service;

import java.io.ObjectOutputStream;
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
    private final ObjectOutputStream out;

    /**
     * Constructor for RegisterCommand.
     *
     * @param authenticationService the authentication service
     * @param out the output stream
     */
    public RegisterCommand(AuthenticationService authenticationService, ObjectOutputStream out) {
        this.authenticationService = authenticationService;
        this.out = out;
    }

    /**
     * Handles a user request.
     *
     * @param userRequest the user request
     * @return true if the user request was handled successfully
     */
    public boolean handle(@NotNull UserRequest userRequest) {

        final User user = userRequest.user();
        final ServerResponse serverResponse;

        if (user.getPasswordHash().isEmpty()) {
            serverResponse = new ServerResponse(-1, "Password cannot be empty.");
        } else if (authenticationService.getUserByUsername(user.getUsername()).isPresent()) {
            serverResponse = new ServerResponse(-1, "User already registered.");
        } else if (authenticationService.add(user)) {
            serverResponse = new ServerResponse(0, "Registration successful.");
        } else {
            serverResponse = new ServerResponse(-1, "Registration failed.");
        }

        return sendResponse(out, serverResponse);
    }
}
