package client.gui.settings;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Dialog to display settings.
 */
@SuppressWarnings("serial")
public class SettingsDialog extends JFrame {

    /**
     * Constructor for the SettingsDialog.
     */
    public SettingsDialog() {

        setTitle("Settings");
        setLocationRelativeTo(null);
        setSize(500, 400);

        final JPanel contentPanel = new JPanel(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        final Dimension buttonSize = new Dimension(140, 50);
        final JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.addActionListener(e -> new ChangePasswordDialog(this));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        changePasswordButton.setPreferredSize(buttonSize);
        contentPanel.add(changePasswordButton, gbc);

        add(contentPanel);
        setVisible(true);
    }
}
