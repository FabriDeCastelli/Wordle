package server;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.model.Command;

/**
 * Tests for the Command interface.
 */
public class CommandTests {

    private final Command command = mock(Command.class);



    @Test
    @DisplayName(" can correctly send a response")
    public void testSendResponse() {

        // TODO
    }

    @Test
    @DisplayName(" throws an exception when the response cannot be sent")
    public void testSendResponseThrowsException() {
        // TODO
    }
}
