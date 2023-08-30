package server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for UserServiceManager class.
 */
@DisplayName("The UserServiceManager tests ")
public class UserServiceManagerTests {

    private final UserServiceManager userServiceManager =
            UserServiceManager.getInstance("src/test/java/server/conf/usersTest.json");
    final UserServiceManager anotherUserServiceManager =
            UserServiceManager.getInstance("src/test/java/server/conf/usersTest.json");

    @Test
    @DisplayName(" correctly gets all registered users")
    public void testGetUsersData() {
        assertNotNull(userServiceManager.getUsersMap());
    }

    @Test
    @DisplayName(" correctly returns the same instance")
    public void testGetInstance() {
        assertEquals(userServiceManager, anotherUserServiceManager);
    }

    @Test
    @DisplayName(" has data consistent among instances")
    public void testGetUsersDataConsistency() {
        assertEquals(userServiceManager.getUsersMap(), anotherUserServiceManager.getUsersMap());
    }


}
