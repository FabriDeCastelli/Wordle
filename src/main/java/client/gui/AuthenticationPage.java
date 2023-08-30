package client.gui;

import client.WordleClientMain;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serial;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.enums.AuthType;

/**
 * Interface for the authentication page.
 * The authentication can be done by either logging in or registering.
 * The server can handle both of these requests.
 */
public class AuthenticationPage
        extends JFrame
        implements ActionListener, AuthenticationDialog.AuthDialogListener {

    /**
     * Required by the PMD.
     */
    @Serial
    private static final long serialVersionUID = 4328743;


    /**
     * Constructor for the AuthenticationPage.
     */
    public AuthenticationPage() {

        setTitle("Wordle");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                WordleClientMain.closeResources();
            }
        });
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        final JPanel buttonPanel = new JPanel(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        final JButton button1 = new JButton("LOGIN");
        final JButton button2 = new JButton("REGISTER");

        final Dimension buttonSize = new Dimension(100, 50);
        button1.setPreferredSize(buttonSize);
        button2.setPreferredSize(buttonSize);

        button1.addActionListener(this);
        button2.addActionListener(this);

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(button1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonPanel.add(button2, gbc);

        add(buttonPanel, BorderLayout.CENTER);
    }

    /**
     * On a button click, open the AuthenticationDialog, based on the button clicked.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        final JButton sourceButton = (JButton) e.getSource();
        final String buttonText = sourceButton.getText();
        new AuthenticationDialog(AuthType.valueOf(buttonText), this).setVisible(true);
    }

    @Override
    public void onAuthDialogClose() {
        dispose();
    }

}
