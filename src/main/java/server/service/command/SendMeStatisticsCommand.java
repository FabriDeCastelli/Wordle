package server.service.command;

import model.Request;
import model.Response;
import model.UserStatistics;
import model.enums.RequestType;
import model.enums.Status;
import org.jetbrains.annotations.NotNull;
import server.model.Command;
import server.service.UserStatisticsService;

/**
 * Sends the statistics of the logged user to the client.
 */
public class SendMeStatisticsCommand implements Command {

    private final UserStatisticsService userStatisticsService;

    /**
     * Constructor for the SendMeStatisticsCommand.
     *
     * @param userStatisticsService the user statistics service
     */
    public SendMeStatisticsCommand(
            @NotNull UserStatisticsService userStatisticsService) {
        this.userStatisticsService = userStatisticsService;
    }

    @Override
    public Response handle(@NotNull Request request) {

        if (request.requestType() != RequestType.SENDMESTATISTICS) {
            throw new IllegalArgumentException("Cannot handle a non-SENDMESTATISTICS request");
        }

        final String username = request.username();
        final UserStatistics userStatistics =
                userStatisticsService.getStatisticsByUsername(username);
        return new Response(Status.SUCCESS, userStatistics);
    }
}
