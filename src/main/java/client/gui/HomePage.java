package client.gui;

import client.WordleClientMain;
import client.gui.authentication.AuthenticationPage;
import client.gui.play.PlayPage;
import client.gui.settings.SettingsDialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
@SuppressWarnings("serial")
public class HomePage extends JFrame {

    /**
     * Constructor for the HomePage.
     *
     * @param username the name of the user in the session
     */
    public HomePage(String username) {
        setTitle("Home" + " - " + username);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                WordleClientMain.logout();
                WordleClientMain.closeResources();
            }
        });
        setSize(600, 400);
        setLocationRelativeTo(null);

        final JPanel contentPanel = new JPanel(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        final Dimension buttonSize = new Dimension(100, 50);
        final JButton settingsButton = getSettingsButton();

        gbc.gridx = 0;
        gbc.gridy = 0;
        settingsButton.setPreferredSize(buttonSize);
        contentPanel.add(settingsButton, gbc);

        final JButton notificationsButton = getNotificationsButton();
        gbc.gridx = 1;
        notificationsButton.setPreferredSize(buttonSize);
        contentPanel.add(notificationsButton, gbc);

        final JButton playButton = getPlayButton(username);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        playButton.setPreferredSize(buttonSize);
        contentPanel.add(playButton, gbc);

        final JButton showMyStatsButton = getShowMyStatisticsButton(username);
        gbc.gridy = 2;
        showMyStatsButton.setPreferredSize(buttonSize);
        contentPanel.add(showMyStatsButton, gbc);

        final JButton logoutButton = getLogoutButton(username);
        gbc.gridy = 3;
        logoutButton.setPreferredSize(buttonSize);
        contentPanel.add(logoutButton, gbc);

        add(contentPanel);
    }

    @NotNull
    @SuppressWarnings("unchecked")
    private JButton getNotificationsButton() {
        final JButton notificationsButton = new JButton("Notifications");
        notificationsButton.addActionListener(e -> {
            final Optional<Response> response = WordleClientMain.showMeSharing();
            if (response.isEmpty()) {
                JOptionPane.showMessageDialog(HomePage.this, "Server couldn't respond.");
            } else if (response.get().status() == Status.SUCCESS) {
                if (response.get().data() instanceof Queue<?>) {
                    new NotificationDialog(this,
                            (Queue<Notification>) response.get().data());
                }
            } else {
                JOptionPane.showMessageDialog(HomePage.this, response.get().message());
            }
        });
        return notificationsButton;
    }

    @NotNull
    private JButton getSettingsButton() {
        final JButton settingsButton =  new JButton("Settings");
        settingsButton.addActionListener(e -> {
            new SettingsDialog();
        });
        return settingsButton;
    }

    @NotNull
    private JButton getLogoutButton(String username) {
        final JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            final Optional<Response> response = WordleClientMain.logout();
            if (response.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Server could not respond.");
            } else if (response.get().status() == Status.SUCCESS) {
                new AuthenticationPage().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, response.get().message());
            }
        });
        return logoutButton;
    }

    @NotNull
    private JButton getShowMyStatisticsButton(String username) {
        final JButton showMyStatsButton = new JButton("My statistics");
        showMyStatsButton.addActionListener(e -> {
            final Optional<Response> response = WordleClientMain.sendMeStatistics();
            if (response.isEmpty()) {
                JOptionPane.showMessageDialog(HomePage.this, "Server could not respond.");
            } else if (response.get().status() == Status.SUCCESS) {
                new StatisticsDialog(this, username, (UserStatistics) response.get().data());
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
            final Optional<Response> response = WordleClientMain.play();
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