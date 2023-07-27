package gui;

import enums.AuthType;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Interface for the authentication page.
 * The authentication can be done by either logging in or registering.
 * The server can handle both of these requests.
 */
public class AuthenticationPage extends JFrame implements ActionListener {

    /**
     * Required by the PMD.
     */
    public static final long serialVersionUID = 4328743;


    /**
     * Constructor for the AuthenticationPage.
     */
    public AuthenticationPage() {

        setTitle("Wordle");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 170);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 50));

        final JButton button1 = new JButton("Login");
        final JButton button2 = new JButton("Register");

        final Dimension buttonSize = new Dimension(100, 50);
        button1.setPreferredSize(buttonSize);
        button2.setPreferredSize(buttonSize);

        button1.addActionListener(this);
        button2.addActionListener(this);

        buttonPanel.add(button1);
        buttonPanel.add(button2);

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
        new AuthenticationDialog(AuthType.valueOf(buttonText)).setVisible(true);

    }

}
