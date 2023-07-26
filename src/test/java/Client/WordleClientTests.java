package Client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * WordleClientTests is the test suite for WordleClient.
 */
@DisplayName("The wordle client test ")
public class WordleClientTests {

    @Test
    @DisplayName("can correctly construct a wordle client")
    public void testWordleClientConstructor() {
        WordleClient client = new WordleClient(1);
        assertEquals(1, client.getId());
    }

}