package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import model.enums.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for Response class.
 */
@DisplayName("The Response tests ")
public class ResponseTests {

    private final Response response = new Response(Status.FAILURE, "testMessage");

    @Test
    @DisplayName(" can correctly get the status")
    public void testGetStatus() {
        assertEquals(Status.FAILURE, response.status());
    }

    @Test
    @DisplayName(" can correctly get the message")
    public void testGetMessage() {
        assertEquals("testMessage", response.message());
    }

}
