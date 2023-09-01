package server.service.command;

import java.util.ArrayList;
import java.util.List;
import model.GameResult;
import model.Request;
import model.Response;
import model.UserStatistics;
import model.WordAttempt;
import model.WordHints;
import model.enums.RequestType;
import model.enums.Status;
import org.jetbrains.annotations.NotNull;
import server.model.Command;
import server.service.PlayWordleService;
import server.service.UserStatisticsService;
import server.service.WordExtractionService;

/**
 * Handles the logic of the game when guessing a certain word.
 */
public class SendWordCommand implements Command {

    private final PlayWordleService playWordleService;
    private final UserStatisticsService userStatisticsService;
    private final List<WordHints> wordHintsHistory;


    /**
     * Constructor for GuessWordCommand.
     *
     * @param playWordleService     the play wordle service
     * @param userStatisticsService the user statistics service
     */
    public SendWordCommand(
            @NotNull PlayWordleService playWordleService,
            @NotNull UserStatisticsService userStatisticsService) {
        this.playWordleService = playWordleService;
        this.userStatisticsService = userStatisticsService;
        this.wordHintsHistory = new ArrayList<>();
    }

    @Override
    public Response handle(@NotNull Request request) {

        if (request.requestType() != RequestType.SENDWORD) {
            throw new IllegalArgumentException("Cannot handle a non-SENDWORD request");
        }

        final WordAttempt wordAttempt = getWordAttempt(request);

        if (wordAttempt.attemptNumber() - 11 > 0) {
            final String loggedUsername = request.username();
            final UserStatistics userStatistics =
                    userStatisticsService.getStatisticsByUsername(loggedUsername);
            userStatistics.resetCurrentStreak();
            userStatisticsService.updateStatistics(loggedUsername, userStatistics);
            final GameResult gameResult =
                    new GameResult(new ArrayList<>(wordHintsHistory), userStatistics);
            wordHintsHistory.clear();
            return new Response(Status.SUCCESS,
                    "You have exceeded the maximum number of attempts.", gameResult);
        }

        final WordHints wordHints = playWordleService.guessWord(wordAttempt.word());
        wordHintsHistory.add(wordHints);

        if (wordAttempt.word().equals(WordExtractionService.getCurrentWord())) {
            final String loggedUsername = request.username();
            final UserStatistics userStatistics =
                    userStatisticsService.getStatisticsByUsername(loggedUsername);
            userStatistics.incrementGamesWon();
            userStatistics.incrementCurrentStreak();
            userStatistics.addTrials(wordAttempt.attemptNumber());
            userStatisticsService.updateStatistics(loggedUsername, userStatistics);
            final GameResult gameResult =
                    new GameResult(new ArrayList<>(wordHintsHistory), userStatistics);
            wordHintsHistory.clear();
            return new Response(Status.GUESS, "You guessed the word!", gameResult);
        }

        return new Response(Status.TRYAGAIN, wordHints);

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
