package server.service;

import java.io.ObjectOutputStream;
import java.util.Optional;
import model.ServerResponse;
import model.User;
import model.UserRequest;
import server.model.Command;

/**
 * Login command.
 */
public class LoginCommand implements Command {

    private final AuthenticationService authenticationService;
    private final ObjectOutputStream out;

    /**
     * Constructor for LoginCommand.
     *
     * @param authenticationService the authentication service
     * @param out the output stream
     */
    public LoginCommand(AuthenticationService authenticationService, ObjectOutputStream out) {
        this.authenticationService = authenticationService;
        this.out = out;
    }

    /**
     * Handles a user request.
     *
     * @param userRequest the user request
     * @return true if the user request was handled successfully
     */
    public boolean handle(UserRequest userRequest) {

        final User user = userRequest.user();
        final Optional<User> isRegistered =
                authenticationService.getUserByUsername(user.getUsername());
        final ServerResponse response;

        if (isRegistered.isEmpty()) {
            response = new ServerResponse(-1, "User not registered.");
        } else if (isRegistered.get().equals(user)) {
            response = new ServerResponse(0, "Login successful.");
        } else {
            response = new ServerResponse(-1, "Wrong username or password.");
        }
        return sendResponse(out, response);
    }
}

