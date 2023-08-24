package server.service.command;

import model.Request;
import model.Response;
import model.UserStatistics;
import model.WordAttempt;
import model.enums.RequestType;
import model.enums.Status;
import org.jetbrains.annotations.NotNull;
import server.model.Command;
import server.service.PlayWordleService;
import server.service.UserStatisticsService;
import server.service.WordExtractionService;


/**
 * Guess word command.
 */
public class SendWordCommand implements Command {

    private final PlayWordleService playWordleService;
    private final UserStatisticsService userStatisticsService;


    /**
     * Constructor for GuessWordCommand.
     *
     * @param playWordleService the play wordle service
     * @param userStatisticsService the user statistics service
     */
    public SendWordCommand(
            PlayWordleService playWordleService,
            UserStatisticsService userStatisticsService) {
        this.playWordleService = playWordleService;
        this.userStatisticsService = userStatisticsService;

    }

    @Override
    public Response handle(@NotNull Request request) {

        if (request.requestType() != RequestType.SENDWORD) {
            throw new IllegalArgumentException("Cannot handle a non-sendword requestType");
        } else if (request.data() == null) {
            throw new IllegalArgumentException("Cannot send a null word");
        }

        final WordAttempt wordAttempt = getWordAttempt(request);


        if (wordAttempt.attemptNumber() - 11 > 0) {
            final UserStatistics userStatistics =
                    userStatisticsService.getStatisticsByUsername(request.username());
            userStatistics.resetCurrentStreak();
            userStatisticsService.updateStatistics(request.username(), userStatistics);
            return new Response(Status.SUCCESS,
                    "You have exceeded the maximum number of attempts.", userStatistics);
        }

        if (wordAttempt.word().equals(WordExtractionService.getCurrentWord())) {
            final UserStatistics userStatistics =
                    userStatisticsService.getStatisticsByUsername(request.username());
            userStatistics.incrementGamesWon();
            userStatistics.incrementCurrentStreak();
            userStatistics.addTrials(wordAttempt.attemptNumber());
            userStatisticsService.updateStatistics(request.username(), userStatistics);
            return new Response(Status.GUESS, "You guessed the word!", userStatistics);
        }

        return new Response(Status.TRYAGAIN, playWordleService.guessWord(wordAttempt.word()));

    }


    /**
     * Gets the word attempt from the request.
     *
     * @param request the request
     * @return the word attempt
     */
    private WordAttempt getWordAttempt(Request request) {
        final WordAttempt wordAttempt = (WordAttempt) request.data();
        if (wordAttempt == null) {
            throw new IllegalArgumentException("Cannot send a null word");
        }
        return wordAttempt;
    }

}
