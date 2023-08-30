package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user's statistics on played games.
 */
public class UserStatistics implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int gamesPlayed;
    private int gamesWon;
    private double gamesWonPercentage;
    private int currentStreak;
    private int longestStreak;
    private final List<Integer> guessDistribution;


    /**
     * Constructor for the UserStatistics.
     */
    public UserStatistics() {
        this.gamesPlayed = 0;
        this.gamesWon = 0;
        this.gamesWonPercentage = 0;
        this.currentStreak = 0;
        this.longestStreak = 0;
        this.guessDistribution = new ArrayList<>();
    }


    public int getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     * Increments the number of games played and updates the games won percentage.
     */
    public void incrementGamesPlayed() {
        this.gamesPlayed++;
        if (gamesPlayed != 0) {
            this.gamesWonPercentage = (double) gamesWon / gamesPlayed;
        }
    }

    public int getGamesWon() {
        return gamesWon;
    }

    /**
     * Increments the number of games won and updates the games won percentage.
     */
    public void incrementGamesWon() {
        this.gamesWon++;
        if (gamesPlayed != 0) {
            this.gamesWonPercentage = (double) gamesWon / gamesPlayed;
        }
    }

    public double getGamesWonPercentage() {
        return gamesWonPercentage;
    }


    public int getCurrentStreak() {
        return currentStreak;
    }

    /**
     * Increments the current streak and updates the longest streak.
     */
    public void incrementCurrentStreak() {
        this.currentStreak++;
        if (currentStreak > longestStreak) {
            this.longestStreak = currentStreak;
        }
    }

    /**
     * Resets the current streak.
     */
    public void resetCurrentStreak() {
        this.currentStreak = 0;
    }

    public int getLongestStreak() {
        return longestStreak;
    }


    public List<Integer> getGuessDistribution() {
        return guessDistribution;
    }

    /**
     * Adds the number of trials to the guess distribution.
     *
     * @param trials the number of trials
     */
    public void addTrials(int trials) {
        this.guessDistribution.add(trials);
    }

}
