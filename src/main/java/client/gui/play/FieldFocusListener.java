package client.gui.play;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;


/**
 * Interface to play Wordle.
 */
public class FieldFocusListener implements KeyListener {

    private final int currentIndex;
    private final JTextField[] inputFields;

    /**
     * Constructor for the FieldFocusListener.
     *
     * @param currentIndex the index of the field
     * @param inputFields the array of input fields
     */
    public FieldFocusListener(int currentIndex, JTextField[] inputFields) {
        this.currentIndex = currentIndex;
        this.inputFields = inputFields;
    }

    @Override
    public void keyTyped(KeyEvent e) {

        if (e.getKeyChar() != KeyEvent.CHAR_UNDEFINED
                && e.getKeyChar() != KeyEvent.VK_BACK_SPACE
                && currentIndex < inputFields.length - 1) {
            inputFields[currentIndex + 1].setEditable(true);
            inputFields[currentIndex + 1].requestFocus();

        } else if (!inputFields[currentIndex].getText().isEmpty()) {
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            if (currentIndex > 0) {
                inputFields[currentIndex - 1].requestFocus();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}