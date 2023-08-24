package server.service.command;

import model.Request;
import model.Response;
import model.UserStatistics;
import model.enums.Status;
import org.jetbrains.annotations.NotNull;
import server.model.Command;
import server.service.UserStatisticsService;

/**
 * Command to show the user its statistics.
 */
public class SendMeStatisticsCommand implements Command {

    private final UserStatisticsService userStatisticsService;

    /**
     * Constructor for the SendMeStatisticsCommand.
     *
     * @param userStatisticsService the user statistics service
     */
    public SendMeStatisticsCommand(@NotNull UserStatisticsService userStatisticsService) {
        this.userStatisticsService = userStatisticsService;
    }

    @Override
    public Response handle(@NotNull Request request) {
        final UserStatistics userStatistics =
                userStatisticsService.getStatisticsByUsername(request.username());
        return new Response(Status.SUCCESS, userStatistics);
    }
}
