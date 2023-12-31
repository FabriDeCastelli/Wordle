package client.gui;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import model.UserStatistics;
import org.jetbrains.annotations.NotNull;

/**
 * Interface for the statistics dialog.
 */
@SuppressWarnings("serial")
public class StatisticsDialog extends JDialog {


    /**
     * Constructor for the StatisticsDialog.
     *
     * @param parent the parent frame
     * @param username the name of the user in the session
     * @param userStatistics the user's statistics
     */
    public StatisticsDialog(
            @NotNull JFrame parent,
            @NotNull String username,
            @NotNull UserStatistics userStatistics) {
        super(parent, "Statistics for " + username, true);
        setLayout(new BorderLayout());

        final JTextPane statisticsTextPane = new JTextPane();
        statisticsTextPane.setContentType("text/html");
        statisticsTextPane.setText(DialogUtils.getFormattedStatistics(username, userStatistics));
        statisticsTextPane.setEditable(false);

        final JScrollPane scrollPane = new JScrollPane(statisticsTextPane);
        add(scrollPane, BorderLayout.CENTER);

        setSize(450, 500);
        setLocationRelativeTo(parent);
        setVisible(true);
    }



}
