package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for the WordAttempt class.
 */
@DisplayName("The WordAttempt tests")
public class WordAttemptTests {

    @Test
    @DisplayName("can construct an object with a word and attempt number")
    void canConstructObjectWithWordAndAttemptNumber() {
        final WordAttempt wordAttempt = new WordAttempt("word", 1);
        assertEquals("word", wordAttempt.word());
        assertEquals(1, wordAttempt.attemptNumber());
    }

}
