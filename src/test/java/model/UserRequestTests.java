package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import model.enums.Request;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for user request.
 */
@DisplayName("The UserRequest tests ")
public class UserRequestTests {

    private static User user;

    @BeforeAll
    public static void setUp() {
        user = new User("testUser", "testPassword");
    }

    @Test
    @DisplayName(" can correctly get the request")
    public void testGetRequest() {
        final UserRequest userRequest =
                new UserRequest(Request.LOGIN, user);
        assertEquals(Request.LOGIN, userRequest.request());
    }

    @Test
    @DisplayName(" can correctly get the user")
    public void testGetUser() {
        final UserRequest userRequest =
                new UserRequest(Request.LOGIN, user);
        assertEquals(user, userRequest.user());
    }

}
