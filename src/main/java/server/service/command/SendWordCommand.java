package server.service.command;

import model.Request;
import model.Response;
import model.UserStatistics;
import model.WordAttempt;
import model.enums.RequestType;
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

        if (wordAttempt.word().equals(WordExtractionService.getCurrentWord())) {
            handleCorrectGuess(
                    userStatisticsService.getStatistics(request.username()),
                    wordAttempt,
                    request.username()
            );
            return new Response(1, "You guessed the word!");
        }

        return new Response(0, playWordleService.guessWord(wordAttempt.word()));

    }

    /**
     * Handles a correct guess.
     *
     * @param userStatistics the existing statistics of the user
     * @param wordAttempt the number of trials the user has taken for the current word
     * @param username the username of the user
     */
    private void handleCorrectGuess(
            UserStatistics userStatistics,
            WordAttempt wordAttempt,
            String username) {
        userStatistics.incrementGamesWon();
        userStatistics.incrementCurrentStreak();
        userStatistics.addTrials(wordAttempt.attemptNumber());
        userStatisticsService.updateStatistics(username, userStatistics);
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
