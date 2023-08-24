package client.gui;

import java.awt.BorderLayout;
import java.io.Serial;
import java.util.Queue;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.Notification;
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

        super(parent, "Notifications for " + username, true);
        setLayout(new BorderLayout());

        final JPanel notificationsPanel = new JPanel();
        notificationsPanel.setLayout(new BoxLayout(notificationsPanel, BoxLayout.Y_AXIS));

        notifications.stream()
                .map(notification -> new JLabel(notification.message()))
                .forEach(notificationsPanel::add);

        final JScrollPane scrollPane = new JScrollPane(notificationsPanel);
        add(scrollPane, BorderLayout.CENTER);

        setSize(400, 450);
        setLocationRelativeTo(parent);
        setVisible(true);
    }



}
