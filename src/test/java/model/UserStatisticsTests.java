package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for the UserStatistics class.
 */
@DisplayName("The UserStatistics tests ")
public class UserStatisticsTests {

    private UserStatistics userStatistics;

    @BeforeEach
    public void setUp() {
        userStatistics = new UserStatistics();
    }

    @Test
    @DisplayName(" can successfully increment the played games")
    public void testIncrementGamesPlayed() {
        userStatistics.incrementGamesPlayed();
        assertEquals(1, userStatistics.getGamesPlayed());
    }

    @Test
    @DisplayName(" can successfully increment the won games")
    public void testIncrementGamesWon() {
        userStatistics.incrementGamesWon();
        assertEquals(1, userStatistics.getGamesWon());
    }

    @Test
    @DisplayName(" can successfully get the won games percentage")
    public void testGamesWonPercentage() {
        userStatistics.incrementGamesPlayed();
        userStatistics.incrementGamesWon();
        assertEquals(1.0, userStatistics.getGamesWonPercentage());
    }

    @Test
    @DisplayName(" can successfully increment the current streak")
    public void testIncrementCurrentStreak() {
        userStatistics.incrementCurrentStreak();
        assertEquals(1, userStatistics.getCurrentStreak());
        assertEquals(1, userStatistics.getLongestStreak());
    }

    @Test
    @DisplayName(" can successfully reset the current streak")
    public void testResetCurrentStreak() {
        userStatistics.incrementCurrentStreak();
        userStatistics.resetCurrentStreak();
        assertEquals(0, userStatistics.getCurrentStreak());
    }

    @Test
    @DisplayName(" can successfully add trials")
    public void testAddTrials() {
        userStatistics.addTrials(5);
        assertEquals(1, userStatistics.getGuessDistribution().size());
        assertEquals(5, userStatistics.getGuessDistribution().get(0));
    }
}
