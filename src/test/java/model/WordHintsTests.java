package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * Tests for the WordHints class.
 */
@DisplayName("The WordHints tests ")
public class WordHintsTests {

    @Test
    @DisplayName(" can successfully construct a WordHints object")
    public void testWordHintsRecord() {
        List<Integer> correctPositions = Arrays.asList(1, 3);
        List<Integer> presentLetters = Arrays.asList(0, 2);

        WordHints wordHints = new WordHints(correctPositions, presentLetters);

        assertEquals(correctPositions, wordHints.correctPositions());
        assertEquals(presentLetters, wordHints.presentLetters());
    }
}
