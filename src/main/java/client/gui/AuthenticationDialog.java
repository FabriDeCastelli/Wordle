package client.gui;

import client.WordleClientMain;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.Optional;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import model.Response;
import model.enums.AuthType;
import model.enums.Status;

/**
 * Dialog to perform an authentication, that can be either login or register.
 */
public class AuthenticationDialog extends JFrame implements ActionListener {

    /**
     * Required by the PMD.
     */
    @Serial
    private static final long serialVersionUID = 4328743;

    /**
     * Interface for the AuthenticationDialogListener.
     */
    public interface AuthDialogListener {
        void onAuthDialogClose();
    }

    private final AuthDialogListener resultListener;
    private final Container container = getContentPane();
    private final JLabel usernameLabel = new JLabel("Username:");
    private final JLabel passwordLabel = new JLabel("Password:");
    private final JTextField usernameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JCheckBox showPassword = new JCheckBox("Show Password");
    private final JButton authButton;
    private final AuthType authType;

    /**
     * Constructor for the AuthenticationDialog.
     *
     * @param authType the type of authentication
     */
    public AuthenticationDialog(AuthType authType, AuthDialogListener listener) {

        this.authType = authType;
        this.resultListener = listener;

        authButton = new JButton(authType.name());

        setLayoutManager();
        setLocationAndSize();
        setBorders();
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
        showPassword.setBounds(150, 160, 150, 30);
        authButton.setBounds(150, 200, 120, 30);
    }

    private void setBorders() {
        final Border roundedBorder =
                new CompoundBorder(
                        new LineBorder(Color.GRAY), new EmptyBorder(5, 10, 5, 10));
        usernameField.setBorder(roundedBorder);
        passwordField.setBorder(roundedBorder);

    }

    private void addComponentsToContainer() {
        container.add(usernameLabel);
        container.add(passwordLabel);
        container.add(usernameField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(authButton);
    }

    private void addActionEvent() {
        authButton.addActionListener(this);
        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('•');
            }
        });
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        final Optional<Response> response = WordleClientMain.authenticate(
                authType, usernameField.getText(), new String(passwordField.getPassword()));

        if (response.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Server could not respond.");
        } else if (response.get().status() == Status.FAILURE) {
            JOptionPane.showMessageDialog(this, response.get().message());
            usernameField.setText("");
            passwordField.setText("");
        } else {
            resultListener.onAuthDialogClose();
            new HomePage(usernameField.getText()).setVisible(true);
            dispose();
        }
    }


}
