package client.gui.play;

import client.WordleClientMain;
import client.gui.ShareDialog;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import model.GameResult;
import model.Response;
import model.WordHints;
import model.enums.Status;

/**
 * Interface to play Wordle.
 */
public class PlayPage extends JFrame implements ActionListener {

    private final String username;
    private final Container container = getContentPane();
    private final JTextField[][] inputFields = new JTextField[12][10];
    private final JPanel inputPanel =
            new JPanel(new GridLayout(12, 10, 5, 5));
    private JButton submitButton;
    private int currentAttempt = 0;

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
        createComponents();
        addComponentsToContainer();

        setTitle("Guess the word!");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setLayoutManager() {
        container.setLayout(new BorderLayout());
        container.setPreferredSize(new Dimension(800, 1000));
    }

    private void createComponents() {
        final Border roundedBorder =
                new CompoundBorder(
                        new LineBorder(Color.GRAY), new EmptyBorder(5, 10, 5, 10));

        IntStream.range(0, 12).forEach(i -> {
            final JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
            IntStream.range(0, 10).forEach(j -> {
                inputFields[i][j] = new JTextField(1);
                inputFields[i][j].setEditable(false);
                inputFields[i][j].setBorder(roundedBorder);
                inputFields[i][j].getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        submitButton.setEnabled(checkFieldsFilled());
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        submitButton.setEnabled(checkFieldsFilled());
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        submitButton.setEnabled(checkFieldsFilled());
                    }
                });
                inputFields[i][j].addKeyListener(new FieldFocusListener(j, inputFields[i]));
                rowPanel.add(inputFields[i][j]);
            });
            inputPanel.add(rowPanel);
        });

        inputFields[0][0].setEditable(true);

        submitButton = new JButton("Submit");
        submitButton.setBorder(
                new CompoundBorder(
                        new LineBorder(Color.DARK_GRAY),
                        new EmptyBorder(10, 20, 10, 20)));
        submitButton.addActionListener(this);
    }

    private void addComponentsToContainer() {
        container.add(submitButton, BorderLayout.SOUTH);
        container.add(inputPanel, BorderLayout.CENTER);
    }

    /**
     * Checks if all the fields in the current attempt are filled.
     *
     * @return true if all fields are filled, false otherwise
     */
    public boolean checkFieldsFilled() {
        return Arrays.stream(inputFields[currentAttempt])
                .noneMatch(field -> field.getText().isEmpty());
    }

    /**
     * On a button click, send the user's guess to the server.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (!checkFieldsFilled()) {
            JOptionPane.showMessageDialog(this, "Please complete the word to try.");
            return;
        }

        final StringBuilder guess = new StringBuilder();
        Arrays.stream(inputFields[currentAttempt])
                .forEach(field -> guess.append(field.getText()));

        final Optional<Response> response =
                WordleClientMain.sendWord(guess.toString(), currentAttempt + 1);

        if (response.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Server could not respond.");
            dispose();
        } else if (response.get().status() == Status.TRYAGAIN) {
            Arrays.stream(inputFields[currentAttempt]).forEach(elem -> elem.setEditable(false));
            final WordHints wordHints = (WordHints) response.get().data();
            wordHints.correctPositions()
                    .forEach(i -> inputFields[currentAttempt][i].setBackground(Color.GREEN));
            wordHints.presentLetters()
                    .forEach(i -> inputFields[currentAttempt][i].setBackground(Color.ORANGE));
            currentAttempt++;
            inputFields[currentAttempt][0].setEditable(true);
        } else {
            final GameResult gameResult = (GameResult) response.get().data();
            JOptionPane.showMessageDialog(this, response.get().message());
            new ShareDialog(username, gameResult);
            dispose();
        }

    }

}
