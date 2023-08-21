package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for Response class.
 */
@DisplayName("The Response tests ")
public class ResponseTests {

    private final Response response = new Response(123, "testMessage");

    @Test
    @DisplayName(" can correctly get the status")
    public void testGetStatus() {
        assertEquals(123, response.status());
    }

    @Test
    @DisplayName(" can correctly get the message")
    public void testGetMessage() {
        assertEquals("testMessage", response.message());
    }

}
