package server.service;

import java.io.ObjectOutputStream;
import model.ServerResponse;
import model.User;
import model.UserRequest;
import server.model.Command;

/**
 * Logout command.
 */
public class LogoutCommand implements Command {

    private final AuthenticationService authenticationService;
    private final ObjectOutputStream out;

    public LogoutCommand(AuthenticationService authenticationService, ObjectOutputStream out) {
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
        if (authenticationService.getUserByUsername(user.getUsername()).isEmpty()) {
            throw new IllegalStateException("Cannot logout a not registered user");
        }
        return sendResponse(out, new ServerResponse(0, "Logout successful."));
    }

}
