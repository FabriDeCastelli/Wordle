package server;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * WordleServerMainTests is the test suite for WordleServerMain.
 */
@DisplayName("The wordle server test ")
public class WordleServerMainTests {

    @Test
    @DisplayName(" can correctly create a WordleServerMain")
    public void testWordleServer() {
        new WordleServerMain();
    }


}
