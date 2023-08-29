package server.service.command;

import model.Request;
import model.Response;
import model.UserStatistics;
import model.enums.RequestType;
import model.enums.Status;
import org.jetbrains.annotations.NotNull;
import server.model.Command;
import server.service.AuthenticationService;
import server.service.PlayWordleService;
import server.service.UserStatisticsService;
import server.service.WordExtractionService;

/**
 * Checks if the requesting user can play the game and manages statistics.
 */
public class PlayCommand implements Command {

    private final PlayWordleService playWordleService;
    private final UserStatisticsService userStatisticsService;
    private final AuthenticationService authenticationService;

    /**
     * Constructor for PlayCommand.
     *
     * @param playWordleService     the play wordle service
     * @param userStatisticsService the user statistics service
     * @param authenticationService the authentication service
     */
    public PlayCommand(
            @NotNull PlayWordleService playWordleService,
            @NotNull UserStatisticsService userStatisticsService,
            @NotNull AuthenticationService authenticationService) {
        this.playWordleService = playWordleService;
        this.userStatisticsService = userStatisticsService;
        this.authenticationService = authenticationService;
    }

    /**
     * Handles a user requestType.
     *
     * @param request the user requestType
     * @return the server response
     */
    public Response handle(@NotNull Request request) {

        if (request.requestType() != RequestType.PLAY) {
            throw new IllegalArgumentException("Cannot handle a non-PLAY request");
        }

        final String currentWord = WordExtractionService.getCurrentWord();
        final String username = authenticationService.getLoggedUser()
                .orElseThrow(IllegalStateException::new)
                .getUsername();

        if (playWordleService.hasPlayed(username, currentWord)) {
            return new Response(Status.FAILURE, "You have already played the game for this word.");
        } else if (playWordleService.addPlayedGame(username, currentWord)) {
            final UserStatistics userStatistics =
                    userStatisticsService.getStatisticsByUsername(username);
            userStatistics.incrementGamesPlayed();
            return userStatisticsService.updateStatistics(username, userStatistics)
                ? new Response(Status.SUCCESS, "The user can play the game.")
                : new Response(Status.FAILURE, "Error while updating the user statistics.");
        }
        return new Response(Status.FAILURE, "Error while saving the played game.");
    }

}