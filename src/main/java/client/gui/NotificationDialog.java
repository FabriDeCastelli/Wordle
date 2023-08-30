package client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.Serial;
import java.util.List;
import java.util.Queue;
import java.util.stream.IntStream;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import model.Notification;
import model.WordHints;
import org.jetbrains.annotations.NotNull;

/**
 * Allows the user to see their notifications.
 */
public class NotificationDialog extends JDialog {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for the Notification.
     *
     * @param parent the parent frame
     * @param username the name of the user in the session
     */
    public NotificationDialog(
            @NotNull JFrame parent,
            @NotNull String username,
            @NotNull Queue<Notification> notifications) {

        super(parent, "Incoming notifications... ", true);
        setLayout(new BorderLayout());

        final JPanel notificationsPanel = new JPanel();
        notificationsPanel.setLayout(new BoxLayout(notificationsPanel, BoxLayout.Y_AXIS));

        notifications.stream()
                .map(notification -> {
                    final JLabel notificationLabel = new JLabel(notification.message());
                    notificationLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);

                    final JButton openButton = new JButton("Open");
                    openButton.setAlignmentX(JButton.RIGHT_ALIGNMENT);
                    openButton.addActionListener(e -> openNotificationDialog(notification));

                    final JPanel notificationPanel = new JPanel();
                    notificationPanel.setLayout(new BoxLayout(notificationPanel, BoxLayout.X_AXIS));
                    notificationPanel.add(notificationLabel);
                    notificationPanel.add(openButton);

                    return notificationPanel;
                })
                .forEach(notificationsPanel::add);

        final JScrollPane scrollPane = new JScrollPane(notificationsPanel);
        add(scrollPane, BorderLayout.CENTER);

        setSize(400, 450);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    @SuppressWarnings({"all", "unchecked"})
    private void openNotificationDialog(Notification notification) {
        final JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        final JDialog dialog = new JDialog(parentFrame, notification.message(), true);
        dialog.setLayout(new GridLayout(0, 1));

        final List<WordHints> wordHintsHistory = (List<WordHints>) notification.data();
        final int attempts = wordHintsHistory.size();

        final JLabel[][] labelBoxes = new JLabel[attempts][10];

        final Border roundedBorder =
                new CompoundBorder(
                        new LineBorder(Color.GRAY), new EmptyBorder(5, 10, 5, 10));

        final JPanel panel = new JPanel(new GridLayout(attempts, 10));
        panel.setBorder(new EmptyBorder(20, 10, 10, 10));

        IntStream.range(0, attempts).forEach(i -> {
            final JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 1));
            IntStream.range(0, 10).forEach(j -> {
                labelBoxes[i][j] = new JLabel();
                labelBoxes[i][j].setPreferredSize(new Dimension(30, 30));
                labelBoxes[i][j].setBorder(roundedBorder);
                rowPanel.add(labelBoxes[i][j]);
            });

            wordHintsHistory.get(i).correctPositions().forEach(position -> {
                labelBoxes[i][position].setBackground(Color.GREEN);
                labelBoxes[i][position].setOpaque(true);
            });

            wordHintsHistory.get(i).presentLetters().forEach(position -> {
                labelBoxes[i][position].setBackground(Color.ORANGE);
                labelBoxes[i][position].setOpaque(true);
            });

            panel.add(rowPanel);
        });

        dialog.add(new JScrollPane(panel), BorderLayout.CENTER);
        dialog.setSize(380, 120 * attempts);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }


}
