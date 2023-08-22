package server.service.command;

import model.Request;
import model.Response;
import model.User;
import model.UserStatistics;
import model.enums.RequestType;
import org.jetbrains.annotations.NotNull;
import server.model.Command;
import server.service.PlayWordleService;
import server.service.UserStatisticsService;
import server.service.WordExtractionService;

/**
 * Play command.
 */
public class PlayCommand implements Command {

    private final PlayWordleService playWordleService;
    private final UserStatisticsService userStatisticsService;

    /**
     * Constructor for PlayCommand.
     *
     * @param playWordleService the play wordle service
     */
    public PlayCommand(
            PlayWordleService playWordleService,
            UserStatisticsService userStatisticsService) {
        this.playWordleService = playWordleService;
        this.userStatisticsService = userStatisticsService;
    }

    /**
     * Handles a user requestType.
     *
     * @param request the user requestType
     * @return the server response
     */
    public Response handle(@NotNull Request request) {

        if (request.requestType() != RequestType.PLAY) {
            throw new IllegalArgumentException("Cannot handle a non-play requestType");
        } else if (request.username() == null) {
            throw new IllegalArgumentException("Cannot play a null user");
        }

        final String currentWord = WordExtractionService.getCurrentWord();
        final String username = request.username();

        if (playWordleService.hasPlayed(username, currentWord)) {
            return new Response(-1, "You have already played the game for this word.");
        } else if (playWordleService.addPlayedGame(username, currentWord)) {
            final UserStatistics userStatistics = userStatisticsService.getStatistics(username);
            userStatistics.incrementGamesPlayed();
            return userStatisticsService.updateStatistics(username, userStatistics)
                ? new Response(0, "The user can play the game.")
                : new Response(-1, "Error while updating the user statistics.");
        }
        return new Response(-1, "Error while saving the played game.");
    }

}