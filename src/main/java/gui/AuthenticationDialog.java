package gui;

import client.WordleClient;
import enums.AuthType;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Dialog to perform an authentication, that can be either login or register.
 */
public class AuthenticationDialog extends JFrame implements ActionListener {
    /**
     * Required by the PMD.
     */
    public static final long serialVersionUID = 4328743;
    private final Container container = getContentPane();
    private final JLabel usernameLabel = new JLabel("Username:");
    private final JLabel passwordLabel = new JLabel("Password:");
    private final JTextField usernameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JButton authButton;

    /**
     * Constructor for the AuthenticationDialog.
     *
     * @param authType the type of authentication
     */
    public AuthenticationDialog(AuthType authType) {

        authButton = new JButton(authType.name());

        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();

        setTitle(authType.name());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);


    }

    private void setLayoutManager() {
        container.setLayout(null);
        container.setPreferredSize(new Dimension(400, 265));
    }

    private void setLocationAndSize() {
        usernameLabel.setBounds(50, 50, 100, 30);
        passwordLabel.setBounds(50, 120, 100, 30);
        usernameField.setBounds(150, 50, 150, 30);
        passwordField.setBounds(150, 120, 150, 30);
        authButton.setBounds(150, 200, 120, 30);
    }

    private void addComponentsToContainer() {
        container.add(usernameLabel);
        container.add(passwordLabel);
        container.add(usernameField);
        container.add(passwordField);
        container.add(authButton);
    }

    private void addActionEvent() {
        authButton.addActionListener(this);
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
