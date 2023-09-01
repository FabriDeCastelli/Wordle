package server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import model.User;
import model.UserStatistics;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for UserStatistics class.
 */
@DisplayName("The UserStatistics tests ")
public class UserStatisticsServiceTests {

    private static final AuthenticationService authenticationService =
            AuthenticationService.getInstance("src/test/java/server/config/usersTest.json");
    private static final User user = new User("testUser", "testPassword");
    private static final UserStatisticsService userStatisticsService =
            new UserStatisticsService("src/test/java/server/config/usersTest.json");

    /**
     * Set up the test environment.
     */
    @BeforeAll
    public static void setUp() {
        if (!authenticationService.isRegistered("testUser")) {
            authenticationService.register(user);
        }
    }

    @AfterAll
    public static void tearDown() {
        authenticationService.unregister(user);
    }

    @Test
    @DisplayName(" correctly throws exception when updating statistics for not registered user")
    public void testUpdateStatisticsNotRegisteredUSer() {

        assertThrows(IllegalStateException.class, () -> userStatisticsService
                .updateStatistics("surelyNotRegisteredUsername", new UserStatistics()));

    }

    @Test
    @DisplayName(" correctly updates the statistics for registered user")
    public void testUpdateStatisticsRegisteredUser() {

        final UserStatistics userStatistics = new UserStatistics();
        userStatistics.incrementCurrentStreak();
        userStatistics.incrementGamesWon();
        assertTrue(userStatisticsService.updateStatistics(user.getUsername(), userStatistics));
        final UserStatistics updatedUserStatistics =
                userStatisticsService.getStatisticsByUsername(user.getUsername());
        assertEquals(userStatistics, updatedUserStatistics);

    }

    @Test
    @DisplayName(" correctly throws exception when getting statistics for not registered user")
    public void testThrowsGetStatisticsByNotRegisteredUsername() {
        assertThrows(IllegalStateException.class,
                () -> userStatisticsService.getStatisticsByUsername("surelyNotRegisteredUsername"));
    }


}
