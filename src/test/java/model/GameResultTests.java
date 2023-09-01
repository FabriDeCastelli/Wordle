package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for the GameResult class.
 */
@DisplayName("The GameResult tests")
public class GameResultTests {

    @Test
    @DisplayName("can correctly construct an instance of GameResult")
    void testConstructor() {
        final UserStatistics userStatistics = new UserStatistics();
        final WordHints wordHints = new WordHints(List.of(1, 2, 3), List.of(4, 5, 6));
        final List<WordHints> wordHintsHistory = List.of(wordHints, wordHints);
        final GameResult gameResult = new GameResult(wordHintsHistory, userStatistics);
        assertEquals(userStatistics, gameResult.userStatistics());
        assertEquals(wordHintsHistory, gameResult.wordHintsHistory());
    }

}
