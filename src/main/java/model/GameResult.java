package model;

import java.io.Serializable;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a game result.
 * The result of a game is composed by the history of the word hints
 * and the user statistics updated at the end of the game.
 *
 * @param wordHintsHistory the history of the word hints in the played game
 * @param userStatistics the user statistics updated at the end of the game
 */
public record GameResult(@NotNull List<WordHints> wordHintsHistory,
                         @NotNull UserStatistics userStatistics) implements Serializable {

}
