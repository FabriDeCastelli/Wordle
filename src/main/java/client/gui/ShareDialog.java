package client.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import model.UserStatistics;
import org.jetbrains.annotations.NotNull;

/**
 * ShareDialog is a dialog that allows the user to share their statistics.
 */
public class ShareDialog extends JFrame {

    @Serial
    private static final long serialVersionUID = 987654321L;
    private final String username;
    private final UserStatistics userStatistics;
    private final Container container = getContentPane();
    private final JButton shareButton = new JButton("Share");
    private final JButton backToHomePageButton = new JButton("Back to home page");
    private JTextPane statisticsTextPane;

    /**
     * Constructor for the ShareDialog.
     *
     * @param username the name of the user in the session, assumed to be unique
     * @param userStatistics the user's statistics
     */
    public ShareDialog(@NotNull String username, @NotNull UserStatistics userStatistics) {
        this.username = username;
        this.userStatistics = userStatistics;
        setLayoutManager();
        setLocationAndSize();
        addActionEvent();

        setTitle("Share your results!");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setLayoutManager() {
        container.setLayout(new BorderLayout());
        container.setPreferredSize(new Dimension(400, 450));
    }

    private void setLocationAndSize() {
        statisticsTextPane = new JTextPane();
        statisticsTextPane.setContentType("text/html");
        statisticsTextPane.setText(getFormattedStatistics());
        statisticsTextPane.setEditable(false);
        shareButton.setPreferredSize(new Dimension(100, 50));
        backToHomePageButton.setPreferredSize(new Dimension(150, 50));

        final JScrollPane scrollPane = new JScrollPane(statisticsTextPane);
        container.add(scrollPane, BorderLayout.CENTER);

        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(shareButton);
        buttonPanel.add(backToHomePageButton);
        container.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addActionEvent() {
        shareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(ShareDialog.this,
                        "Your results have been shared!",
                        "Share results",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });
        backToHomePageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HomePage(username).setVisible(true);
                dispose();
            }
        });
    }

    /**
     * Returns the formatted statistics as a String.
     *
     * @return the formatted statistics
     */
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    private String getFormattedStatistics() {
        final String cssStyle = "<style>"
                + "html, body { height: 100%; display: flex; justify-content: center; "
                + "align-items: center; background-color: #f5f5f5; }"
                + "div { text-align: center; background-color: white; padding: 20px; "
                + "border-radius: 10px; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2); }"
                + "h1 { color: #333; font-size: 24px; margin-bottom: 10px; }"
                + "p { font-size: 16px; margin-bottom: 5px; }"
                + "</style>";

        final String statisticsContent =
                "<div>"
                    + "<h1>Statistics for " + username + "</h1>"
                    + "<p>Games Played: " + userStatistics.getGamesPlayed() + "</p>"
                    + "<p>Games Won: " + userStatistics.getGamesWon() + "</p>"
                    + "<p>Games Won Percentage: " + userStatistics.getGamesWonPercentage() + "</p>"
                    + "<p>Current Streak: " + userStatistics.getCurrentStreak() + "</p>"
                    + "<p>Longest Streak: " + userStatistics.getLongestStreak() + "</p>"
                    + "<p>Guess Distribution: " + userStatistics.getGuessDistribution() + "</p>"
                + "</div>";

        return "<html>" + cssStyle + "<body>" + statisticsContent + "</body></html>";
    }

}
