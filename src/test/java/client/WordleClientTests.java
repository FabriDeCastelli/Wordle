package client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * WordleClientTests is the test suite for WordleClient.
 */
@DisplayName("The wordle client test")
public class WordleClientTests {

    @Test
    @DisplayName(" can correctly create a WordleClient")
    public void testWordleClient() {
        new WordleClient();
    }

    @Test
    @DisplayName(" can correctly run the main method")
    public void testWordleMain() {
        WordleClient.main(new String[] {});
    }

    @Test
    @DisplayName(" can correctly login")
    public void testLogin() {
        WordleClient.login("username", "password");
    }

    @Test
    @DisplayName(" can correctly register")
    public void testRegister() {
        WordleClient.register("username", "password");
    }

}