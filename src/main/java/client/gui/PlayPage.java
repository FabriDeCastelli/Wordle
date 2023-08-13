package client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.io.Serial;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


/**
 * Interface to play Wordle.
 */
public class PlayPage extends JFrame {

    private final String username;
    private JTextField[] inputFields;
    private JButton submitButton;
    private JTextArea resultArea;
    private int remainingAttempts = 12;

    /**
     * Required by the PMD.
     */
    @Serial
    private static final long serialVersionUID = 987654321L;

    /**
     * Constructor for the PlayPage.
     *
     * @param username the name of the user in the session, assumed to be unique
     */
    public PlayPage(String username) {
        this.username = username;
        setLayoutManager();
        setLocationAndSize();

        setTitle("Wordle");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setLayoutManager() {
        setLayout(new BorderLayout());
    }

    private void setLocationAndSize() {
        inputFields = new JTextField[10];
        JPanel inputPanel = new JPanel(new FlowLayout());

        for (int i = 0; i < 10; i++) {
            inputFields[i] = new JTextField(1);
            // Apply custom border for rounded edges
            final Border roundedBorder =
                    new CompoundBorder(
                            new LineBorder(Color.GRAY), new EmptyBorder(5, 10, 5, 10));
            inputFields[i].setBorder(roundedBorder);
            inputPanel.add(inputFields[i]);
        }

        add(inputPanel, BorderLayout.NORTH);

        submitButton = new JButton("Submit");
        // Apply custom border for button
        final Border buttonBorder =
                new CompoundBorder(
                    new LineBorder(Color.DARK_GRAY), new EmptyBorder(10, 20, 10, 20));
        submitButton.setBorder(buttonBorder);
        resultArea = new JTextArea(12, 30);
        resultArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);
    }

}


