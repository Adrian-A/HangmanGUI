import javax.swing.*;
import java.awt.*;

public class Hangman {
    public static void main(String[] args) {
        HangmanGUI hangmanGUI = new HangmanGUI();
        hangmanGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hangmanGUI.setSize(600, 600);
        hangmanGUI.setVisible(true);
    }
}