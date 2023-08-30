package client.gui;

import client.WordleClientMain;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Optional;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import model.GameResult;
import model.Response;
import model.enums.Status;
import org.jetbrains.annotations.NotNull;

/**
 * ShareDialog is a dialog that allows the user to share their statistics.
 */
@SuppressWarnings("serial")
public class ShareDialog extends JFrame {
    private final String username;
    private final GameResult gameResult;
    private final Container container = getContentPane();
    private final JButton shareButton = new JButton("Share");
    private final JButton backToHomePageButton = new JButton("Back to home page");

    /**
     * Constructor for the ShareDialog.
     *
     * @param username the name of the user in the session, assumed to be unique
     * @param gameResult the game result
     */
    public ShareDialog(@NotNull String username, @NotNull GameResult gameResult) {
        this.username = username;
        this.gameResult = gameResult;
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
        JTextPane statisticsTextPane = new JTextPane();
        statisticsTextPane.setContentType("text/html");
        statisticsTextPane.setText(
                DialogUtils.getFormattedStatistics(username, gameResult.userStatistics()));
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

        shareButton.addActionListener(e -> {
            final Optional<Response> response =
                    WordleClientMain.share(gameResult.wordHintsHistory());
            if (response.isEmpty()) {
                JOptionPane.showMessageDialog(
                        null, "Could not share statistics.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (response.get().status() == Status.FAILURE) {
                JOptionPane.showMessageDialog(
                        null, response.get().message(), "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(
                        null, response.get().message(), "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            dispose();
        });

        backToHomePageButton.addActionListener(e -> {
            new HomePage(username).setVisible(true);
            dispose();
        });
        
    }

}