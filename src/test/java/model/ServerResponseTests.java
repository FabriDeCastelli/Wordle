package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for ServerResponse class.
 */
@DisplayName("The ServerResponse tests ")
public class ServerResponseTests {

    private final ServerResponse serverResponse = new ServerResponse(123, "testMessage");

    @Test
    @DisplayName(" can correctly get the status")
    public void testGetStatus() {
        assertEquals(123, serverResponse.status());
    }

    @Test
    @DisplayName(" can correctly get the message")
    public void testGetMessage() {
        assertEquals("testMessage", serverResponse.message());
    }

}
