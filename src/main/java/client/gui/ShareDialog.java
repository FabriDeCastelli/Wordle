package client.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.io.Serial;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 * Dialog to share the results of the game.
 */
public class ShareDialog extends JFrame {


    /**
     * Required by the PMD.
     */
    @Serial
    private static final long serialVersionUID = 1L;
    private final String username;
    private final Container container = getContentPane();
    private final JButton shareButton = new JButton("Share");
    private final JButton backToPlayPageButton = new JButton("Back to home page");

    /**
     * Constructor for the ShareDialog.
     *
     * @param username the name of the user in the session, assumed to be unique
     */
    public ShareDialog(String username) {
        this.username = username;
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();

        setTitle("Share your results!");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void setLayoutManager() {
        container.setLayout(null);
        container.setPreferredSize(new Dimension(400, 265));
    }

    private void setLocationAndSize() {
        shareButton.setBounds(150, 100, 100, 30);
        backToPlayPageButton.setBounds(150, 150, 200, 30);
    }

    private void addComponentsToContainer() {
        container.add(shareButton);
        container.add(backToPlayPageButton);
    }

    private void addActionEvent() {
        shareButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Your results have been shared!",
                    "Share results",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });
        backToPlayPageButton.addActionListener(e -> {
            new HomePage(username).setVisible(true);
            dispose();
        });
    }



}
