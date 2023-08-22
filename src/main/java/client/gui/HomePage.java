package client.gui;

import client.WordleClientMain;
import client.gui.play.PlayPage;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.Optional;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.Response;
import model.enums.Status;

/**
 * Interface for the home page.
 */
public class HomePage extends JFrame implements ActionListener {

    @Serial
    private static final long serialVersionUID = 1L;
    private final String username;

    /**
     * Constructor for the HomePage.
     *
     * @param username the name of the user in the session
     */
    public HomePage(String username) {
        this.username = username;
        setTitle("Home");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 170));
        final JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this);
        buttonPanel.add(logoutButton);
        add(buttonPanel, BorderLayout.CENTER);

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
        buttonPanel.add(playButton);
        add(buttonPanel, BorderLayout.CENTER);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final Optional<Response> response = WordleClientMain.logout(username);
        if (response.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Server could not respond.");
        } else if (response.get().status() == Status.SUCCESS) {
            JOptionPane.showMessageDialog(this, "Successfully logged out.");
            this.dispose();
            new AuthenticationPage().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Error logging out.");
        }
    }

}
