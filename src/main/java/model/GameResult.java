package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Represents a game result.
 */
public record GameResult(String username, List<WordHints> wordHintsHistory,
                         UserStatistics userStatistics) implements Serializable {

    @Serial
    private static final long serialVersionUID = 987654321L;

    /**
     * Constructor for GameResult.
     *
     * @param username         the username of the user
     * @param wordHintsHistory the history of hints in the game
     * @param userStatistics   the user statistics
     */
    public GameResult {
    }

}
