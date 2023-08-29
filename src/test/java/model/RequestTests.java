package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import model.enums.RequestType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for user requestType.
 */
@DisplayName("The Request tests ")
public class RequestTests {

    private static String username;

    @BeforeAll
    public static void setUp() {
        username = "testUser";
    }

    @Test
    @DisplayName(" can correctly get the requestType")
    public void testGetRequest() {
        final Request request =
                new Request(RequestType.LOGIN, username);
        assertEquals(RequestType.LOGIN, request.requestType());
        final Request anotherRequest =
                new Request(RequestType.LOGOUT);
        assertEquals(RequestType.LOGOUT, anotherRequest.requestType());
    }

}
