package server.service.command;

import model.Request;
import model.Response;
import model.enums.Status;
import org.jetbrains.annotations.NotNull;
import server.model.Command;
import server.service.AuthenticationService;

/**
 * Command that changes the password of the user.
 */
public class ChangePasswordCommand implements Command {

    private final AuthenticationService authenticationService;

    /**
     * Constructor for the ChangePasswordCommand.
     *
     * @param authenticationService the authentication service
     */
    public ChangePasswordCommand(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public Response handle(@NotNull Request request) {

        final String username = request.username();
        final String newPassword = (String) request.data();

        return authenticationService.changePassword(username, newPassword)
                ? new Response(Status.SUCCESS, "Password changed successfully")
                : new Response(Status.FAILURE, "Try with another password");


    }

}
