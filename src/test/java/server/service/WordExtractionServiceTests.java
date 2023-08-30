package server.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for WordExtractionService class.
 */
@DisplayName("The WordExtractionService tests ")
public class WordExtractionServiceTests {

    @Test
    @DisplayName(" correctly extracts a new word")
    public void testExtractWord() {

        IntStream.range(0, 5).forEach(
                i -> {
                    final WordExtractionService wordExtractionService =
                            new WordExtractionService();
                    wordExtractionService.start();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    assertTrue(WordExtractionService.getCurrentWord() != null
                                    && !WordExtractionService.getCurrentWord().isEmpty()
                                    && WordExtractionService.getExtractedWords().contains(
                                    WordExtractionService.getCurrentWord()
                            )
                    );

                }
        );

    }

}

