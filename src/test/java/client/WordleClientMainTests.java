package client;

import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * WordleClientMainTests is the test suite for WordleClientMain.
 */
@DisplayName("The wordle client test")
public class WordleClientMainTests {

    private final String configFile = "src/main/java/client/conf/client.properties";

    @Test
    @DisplayName(" can correctly create a WordleClientMain")
    public void testWordleClient() {
        new WordleClientMain();
    }

    @Test
    @DisplayName(" can properly read data from the configuration file")
    public void testReadConfig() {
        // TODO
    }

    @Test
    @DisplayName(" can correctly throw an exception when the configuration file is not found")
    public void testReadConfigException() {
        // TODO
    }

    @Test
    @DisplayName(" can correctly connect to the server socket")
    public void testWordleMain() {
        // TODO
    }

    @Test
    @DisplayName(" can correctly send a request")
    public void testSendRequest() {
        // TODO

    }

    @Test
    @DisplayName(" can correctly login")
    public void testLogin() {
        // TODO
    }

    @Test
    @DisplayName(" can correctly register")
    public void testRegister() {
        // TODO
    }

}