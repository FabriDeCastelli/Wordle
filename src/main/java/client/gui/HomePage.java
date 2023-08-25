package client.gui;

import client.WordleClientMain;
import client.gui.play.PlayPage;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serial;
import java.util.Optional;
import java.util.Queue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.Notification;
import model.Response;
import model.UserStatistics;
import model.enums.Status;
import org.jetbrains.annotations.NotNull;

/**
 * Interface for the home page.
 */
public class HomePage extends JFrame {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for the HomePage.
     *
     * @param username the name of the user in the session
     */
    public HomePage(String username) {
        setTitle("Home" + " - " + username);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                WordleClientMain.logout(username);
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        final JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        final JPanel bodyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        final JButton settingsButton = getSettingsButton(username);
        headerPanel.add(settingsButton);

        final JButton notificationsButton = getNotificationsButton(username);
        headerPanel.add(notificationsButton);

        final JButton logoutButton = getLogoutButton(username);
        bodyPanel.add(logoutButton);

        final JButton playButton = getPlayButton(username);
        bodyPanel.add(playButton);

        final JButton showMyStatsButton = getShowMyStatisticsButton(username);
        bodyPanel.add(showMyStatsButton);

        add(bodyPanel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);
    }

    @NotNull
    @SuppressWarnings("unchecked")
    private JButton getNotificationsButton(String username) {
        final JButton notificationsButton = new JButton("Notifications");
        notificationsButton.addActionListener(e -> {
            final Optional<Response> response = WordleClientMain.showMeSharing(username);
            if (response.isEmpty()) {
                JOptionPane.showMessageDialog(HomePage.this, "Server couldn't respond.");
            } else if (response.get().status() == Status.SUCCESS) {
                if (response.get().data() instanceof Queue<?>) {
                    new NotificationDialog(this, username,
                            (Queue<Notification>) response.get().data())
                            .setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(HomePage.this, response.get().message());
            }
        });
        notificationsButton.setBounds(50, 10, 100, 30);
        return notificationsButton;
    }

    @NotNull
    private JButton getSettingsButton(String username) {
        return new JButton("Settings");
    }

    @NotNull
    private JButton getLogoutButton(String username) {
        final JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            final Optional<Response> response = WordleClientMain.logout(username);
            if (response.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Server could not respond.");
            } else if (response.get().status() == Status.SUCCESS) {
                JOptionPane.showMessageDialog(this, response.get().message());
                this.dispose();
                new AuthenticationPage().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, response.get().message());
            }
        });
        return logoutButton;
    }

    @NotNull
    private JButton getShowMyStatisticsButton(String username) {
        final JButton showMyStatsButton = new JButton("Show my stats");
        showMyStatsButton.addActionListener(e -> {
            final Optional<Response> response = WordleClientMain.sendMeStatistics(username);
            if (response.isEmpty()) {
                JOptionPane.showMessageDialog(HomePage.this, "Server could not respond.");
            } else if (response.get().status() == Status.SUCCESS) {
                if (response.get().data() instanceof UserStatistics) {
                    new StatisticsDialog(this, username,
                            (UserStatistics) response.get().data()).setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(HomePage.this, response.get().message());
            }
        });
        return showMyStatsButton;
    }

    @NotNull
    private JButton getPlayButton(String username) {
        final JButton playButton = new JButton("Play");

        playButton.addActionListener(e -> {
            final Optional<Response> response = WordleClientMain.play(username);
            if (response.isEmpty()) {
                JOptionPane.showMessageDialog(HomePage.this, "Server could not respond.");
            } else if (response.get().status() == Status.SUCCESS) {
                new PlayPage(username).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(HomePage.this, response.get().message());
            }
        });
        return playButton;
    }


}
