package client.gui;

import model.UserStatistics;

/**
 * Util class to share data across dialogs.
 */
public class DialogUtils {

    /**
     * Returns the formatted statistics as a String.
     *
     * @param username the name of the user in the session, assumed to be unique
     * @param userStatistics the user's statistics
     * @return the formatted statistics
     */
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public static String getFormattedStatistics(String username, UserStatistics userStatistics) {
        final String cssStyle = "<style>"
                + "html, body { height: 100%; display: flex; justify-content: center; "
                + "align-items: center; background-color: #f5f5f5; }"
                + "div { text-align: center; background-color: white; padding: 20px; "
                + "border-radius: 10px; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2); }"
                + "h1 { color: #333; font-size: 24px; margin-bottom: 10px; }"
                + "p { font-size: 16px; margin-bottom: 5px; }"
                + "</style>";

        return "<html>" + cssStyle + "<body>"
                + "<div>"
                + "<h1>Statistics for " + username + "</h1>"
                + "<p>Games Played: " + userStatistics.getGamesPlayed() + "</p>"
                + "<p>Games Won: " + userStatistics.getGamesWon() + "</p>"
                + "<p>Games Won Percentage: " + userStatistics.getGamesWonPercentage() + "</p>"
                + "<p>Current Streak: " + userStatistics.getCurrentStreak() + "</p>"
                + "<p>Longest Streak: " + userStatistics.getLongestStreak() + "</p>"
                + "<p>Guess Distribution: " + userStatistics.getGuessDistribution() + "</p>"
                + "</div>"
                + "</body></html>";
    }
}
