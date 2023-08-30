package client.gui.settings;

import client.WordleClientMain;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import model.Response;

/**
 * Dialog to change password.
 */
@SuppressWarnings("serial")
public class ChangePasswordDialog extends JDialog implements ActionListener {

    private final Container container = getContentPane();
    private final JLabel newPasswordLabel = new JLabel("New Password:");
    private final JPasswordField newPasswordField = new JPasswordField();
    private final JCheckBox showPasswordButton = new JCheckBox("Show Password");
    private final JButton changePasswordButton = new JButton("Change Password");

    /**
     * Constructor for the ChangePasswordDialog.
     *
     * @param parent the parent frame
     */
    public ChangePasswordDialog(Frame parent) {

        super(parent, "Change Password", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayoutManager();
        setLocationAndSize();
        setBorders();
        addComponentsToContainer();
        addActionEvent();

        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void setLayoutManager() {
        container.setLayout(null);
        container.setPreferredSize(new Dimension(400, 265));
    }

    private void setLocationAndSize() {
        newPasswordLabel.setBounds(50, 50, 100, 30);
        newPasswordField.setBounds(150, 50, 150, 30);
        showPasswordButton.setBounds(150, 90, 150, 30);
        changePasswordButton.setBounds(150, 130, 150, 30);
    }

    private void setBorders() {
        final Border roundedBorder =
                new CompoundBorder(
                        new LineBorder(Color.GRAY), new EmptyBorder(5, 10, 5, 10));
        newPasswordField.setBorder(roundedBorder);
    }

    private void addComponentsToContainer() {
        container.add(newPasswordLabel);
        container.add(newPasswordField);
        container.add(showPasswordButton);
        container.add(changePasswordButton);
    }

    private void addActionEvent() {
        showPasswordButton.addActionListener(e -> {
            if (showPasswordButton.isSelected()) {
                newPasswordField.setEchoChar((char) 0);
            } else {
                newPasswordField.setEchoChar('•');
            }
        });

        changePasswordButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final Optional<Response> response =
                WordleClientMain.changePassword(new String(newPasswordField.getPassword()));

        if (response.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Server could not respond.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, response.get().message());
            dispose();
        }
    }
}
