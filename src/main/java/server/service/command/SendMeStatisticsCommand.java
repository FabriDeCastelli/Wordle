package server.service.command;

import model.Request;
import model.Response;
import model.User;
import model.UserStatistics;
import model.enums.Status;
import org.jetbrains.annotations.NotNull;
import server.model.Command;
import server.service.AuthenticationService;
import server.service.UserStatisticsService;

/**
 * Command to show the user its statistics.
 */
public class SendMeStatisticsCommand implements Command {

    private final UserStatisticsService userStatisticsService;
    private final AuthenticationService authenticationService;

    /**
     * Constructor for the SendMeStatisticsCommand.
     *
     * @param userStatisticsService the user statistics service
     * @param authenticationService the authentication service
     */
    public SendMeStatisticsCommand(
            @NotNull UserStatisticsService userStatisticsService,
            @NotNull AuthenticationService authenticationService) {
        this.userStatisticsService = userStatisticsService;
        this.authenticationService = authenticationService;
    }

    @Override
    public Response handle(@NotNull Request request) {
        final User loggedUser =
                authenticationService.getLoggedUser().orElseThrow(IllegalStateException::new);
        final UserStatistics userStatistics =
                userStatisticsService.getStatisticsByUsername(loggedUser.getUsername());
        return new Response(Status.SUCCESS, userStatistics);
    }
}
