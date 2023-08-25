package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for GameResult.
 */
@DisplayName("The GameResult tests")
public class GameResultTests {

    private final GameResult gameResult =
            new GameResult("username", null, null);

    @Test
    @DisplayName("can construct an object with a username, wordHintsHistory and userStatistics")
    void canConstructObjectWithAllArguments() {
        assertEquals("username", gameResult.username());
        assertNull(gameResult.wordHintsHistory());
        assertNull(gameResult.userStatistics());
    }

}
