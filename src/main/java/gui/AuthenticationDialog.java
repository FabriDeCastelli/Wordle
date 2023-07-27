package gui;

import client.WordleClient;
import enums.AuthType;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Dialog to perform an authentication, that can be either login or register.
 */
public class AuthenticationDialog extends JFrame implements ActionListener {
    public static final long serialVersionUID = 4328743;
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    /**
     * Constructor for the AuthenticationDialog.
     *
     * @param authType the type of authentication
     */
    public AuthenticationDialog(AuthType authType) {
        // Set up the JFrame
        setTitle(authType.name());
        // setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null); // Center the window on the screen
        setLayout(new BorderLayout());

        // Create components
        final JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        final JLabel usernameLabel = new JLabel("Username:");
        final JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);

        JButton authButton = new JButton(authType.name());
        authButton.addActionListener(this);

        add(inputPanel, BorderLayout.CENTER);
        add(authButton, BorderLayout.SOUTH);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        final JButton sourceButton = (JButton) e.getSource();
        final String buttonText = sourceButton.getText();

        if (AuthType.Login.name().equals(buttonText)) {
            WordleClient.login(usernameField.getText(), passwordField.getName());
        } else if (AuthType.Register.name().equals(buttonText)) {
            WordleClient.register(usernameField.getText(), passwordField.getName());
        }
    }



}
