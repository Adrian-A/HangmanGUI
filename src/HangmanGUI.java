import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Handles all the GUI related operations such as drawing the hangman and the letters
 * Extends the JFrame class and implements the ActionListener class
 *
 * @author Adrian Alvarez
 */
public class HangmanGUI extends JFrame implements ActionListener{
    /**
     * Object of the HangmanLogic class
     */
    private HangmanLogic hangmanLogic;
    /**
     * Array of JLabels that store the correct letters
     */
    private JLabel[] rightLetters;
    /**
     * Array of 6 JLabels that store the incorrect letters
     */
    private final JLabel[] wrongLetters = new JLabel[6];
    /**
     * Text box that the payer types the word to be guessed into
     */
    private final JTextField wordToGuess;
    /**
     * Text box that the player types the character into
     */
    private final JTextField charGuessed;
    /**
     * Button that the player presses to enter in the word to be guessed
     */
    private final JButton enterButton;
    /**
     * Button that the player presses to guess a letter
     */
    private final JButton guessButton;
    /**
     * Text that instructs the player what to do
     */
    private final JLabel boxLabel;
    /**
     * Keeps track on the amount of times the player guesses wrong
     */
    private int n = 0;
    /**
     * Determines when the program should draw the letter lines
     */
    private boolean draw = false;

    // constructor

    /**
     * Constructor for the HangmanGUI class
     * Sets up the iniiial buttons, texts, and text boxes as well as ActionListeners
     */
    public HangmanGUI(){
        super("Hangman");
        setLayout(null);

        this.hangmanLogic = new HangmanLogic();

        wordToGuess = new JTextField();
        charGuessed = new JTextField();
        enterButton = new JButton("Enter");
        guessButton = new JButton("Guess");
        boxLabel = new JLabel("Enter an all capitalized word for the player to guess");

        wordToGuess.setBounds(135, 270, 230, 30);
        enterButton.setBounds(375, 270, 100, 30);
        boxLabel.setBounds(120, 290, 400, 50);

        add(wordToGuess);
        add(enterButton);
        add(charGuessed);
        charGuessed.setVisible(false);
        add(guessButton);
        guessButton.setVisible(false);
        add(boxLabel);

        enterButton.addActionListener(this);
        guessButton.addActionListener(this);
    }

    // Button pressed method
    /**
     * Determines when the "Guess" or "Enter" button is pressed
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String wordInpt = wordToGuess.getText();
        String userInpt = charGuessed.getText();

        // presses enter button
        if (e.getActionCommand().equals("Enter") && hangmanLogic.wordIsValid(wordInpt)){
            charGuessed.setBounds(185, 5, 230, 30);
            guessButton.setBounds(425, 5, 100, 30);
            boxLabel.setBounds(185, 25, 400, 50);

            hangmanLogic.setWordToGuess(wordInpt);
            rightLetters = new JLabel[hangmanLogic.getNumOfChar()];
            createCorrectLetters();

            wordToGuess.setVisible(false);
            enterButton.setVisible(false);
            guessButton.setVisible(true);
            charGuessed.setVisible(true);
            wordToGuess.setText("");
            boxLabel.setText("Enter a capitalized letter to guess");

            draw = true;
            repaint();
        }

        // presses guess button
        else if (e.getActionCommand().equals("Guess") && hangmanLogic.wordIsValid(userInpt) && hangmanLogic.charIsValid(userInpt.charAt(0)) && userInpt.length() == 1) {
            char charInpt = userInpt.charAt(0);

            // if guessed wrong
            if(!(hangmanLogic.getWordToGuess()).contains(userInpt)){
                hangmanLogic.incorrectGuess(charInpt);

                createWrongLetter(n, 50*n);
                wrongLetters[n].setVisible(true);
                this.n++; // MAYBE HAS THIS

                charGuessed.setText("");
                boxLabel.setText("Enter a capitalized letter to guess");
                repaint();

                // if they use all 6 guesses
                if(hangmanLogic.getRemainingGuesses() == 0){
                    guessButton.setVisible(false);
                    charGuessed.setVisible(false);
                    boxLabel.setVisible(false);
                    JOptionPane.showMessageDialog(null, "GAME OVER! Press X to close");
                }
            }

            // if guessed right
            else if ((hangmanLogic.getWordToGuess()).contains(userInpt) && !(hangmanLogic.getCorrectChars().contains(charInpt))){
                hangmanLogic.getCorrectChars().add(charInpt);

                for (int i = 0; i < hangmanLogic.getNumOfChar(); i++){
                    if (rightLetters[i].getText().charAt(0) == charInpt){
                        rightLetters[i].setVisible(true);
                    }
                }

                boxLabel.setText("Enter a capitalized letter to guess");
                charGuessed.setText("");

                if (hangmanLogic.getCorrectChars().size() == hangmanLogic.totalCorrectChars()){
                    guessButton.setVisible(false);
                    charGuessed.setVisible(false);
                    boxLabel.setVisible(false);
                    JOptionPane.showMessageDialog(null, "YOU WIN! Press X to close");
                }
            }
            else{
                boxLabel.setText("Please enter a valid word");
            }
        }
        else{
            boxLabel.setText("Please enter a valid word");
        }
    }

    // draws the lines for the letters

    /**
     * Draws lines for the letters
     *
     * @param g the specified Graphics window
     */
    private void drawLines(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        int x1 = 20;
        int y = 140;
        int x2 = 60;

        for (int i = 0; i < hangmanLogic.getNumOfChar(); i++) {
            g2d.drawLine(x1, y, x2, y);
            x1 += 50;
            x2 += 50;
        }
    }

    /**
     * Puts the correct letters onto the screen
     */
    private void createCorrectLetters(){
        int x1 = 30;

        // right letters
        for (int i = 0; i < hangmanLogic.getNumOfChar(); i++) {
            rightLetters[i] = new JLabel(String.valueOf(hangmanLogic.getWordToGuess().charAt(i)));

            rightLetters[i].setBounds(x1, 81, 20, 20);
            rightLetters[i].setFont(new Font( "Verdana", Font.PLAIN, 20));

            add(rightLetters[i]);
            rightLetters[i].setVisible(false);

            x1 += 50;
        }
    }

    /**
     * Puts the incorrect letters onto the screen
     *
     * @param num The position in the JLabel array
     * @param xAxis The x position that the letter will be put
     */
    private void createWrongLetter(int num,int xAxis){
        int x = 175;

        wrongLetters[num] = new JLabel(String.valueOf(hangmanLogic.getIncorrectChars().get(num)));

        wrongLetters[num].setBounds(x+xAxis, 500, 20, 20);
        wrongLetters[num].setFont(new Font( "Verdana", Font.PLAIN, 20));

        add(wrongLetters[num]);
        wrongLetters[num].setVisible(false);
    }

    /**
     * Draws the stand for the hangman
     *
     * @param g the specified Graphics window
     */
    private void drawStand(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawLine(205, 450, 205, 150);
        g2d.drawLine(205, 150, 405, 150);
        g2d.drawLine(205, 180, 235, 150);
        g2d.drawLine(405, 150, 405, 200);
    }

    /**
     * Draws the head of the headman
     *
     * @param g the specified Graphics window
     */
    private void drawHead(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawOval(366, 200, 80, 75);
    }

    /**
     * Draws the body of the hangman
     *
     * @param g the specified Graphics window
     */
    private void drawBody(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawLine(405, 275, 405, 375);
    }

    /**
     * Draws the left arm of the hangman
     *
     * @param g the specified Graphics window
     */
    private void drawLeftArm(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawLine(405, 285, 355, 325);
    }

    /**
     * Draws the right arm of the hangman
     *
     * @param g the specified Graphics window
     */
    private void drawRightArm(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawLine(405, 285, 455, 325);
    }

    /**
     * Draws the left leg of the hangman
     *
     * @param g the specified Graphics window
     */
    private void drawLeftLeg(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawLine(405, 375, 355, 415);
    }

    /**
     * Draws the right leg of the hangman
     *
     * @param g the specified Graphics window
     */
    private void drawRightLeg(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawLine(405, 375, 455, 415);
    }

    // paint the lines onto the screen

    /**
     * Puts the hangman stand, body parts, and letter lines onto the screen
     *
     * @param g the specified Graphics window
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (draw) {
            drawLines(g);
            drawStand(g);
        }
        if (hangmanLogic.getRemainingGuesses() == 5) {
            drawHead(g);
        }
        else if (hangmanLogic.getRemainingGuesses() == 4) {
            drawHead(g);
            drawBody(g);
        }
        else if (hangmanLogic.getRemainingGuesses() == 3) {
            drawHead(g);
            drawBody(g);
            drawLeftArm(g);
        }
        else if (hangmanLogic.getRemainingGuesses() == 2) {
            drawHead(g);
            drawBody(g);
            drawLeftArm(g);
            drawRightArm(g);
        }
        else if (hangmanLogic.getRemainingGuesses() == 1) {
            drawHead(g);
            drawBody(g);
            drawLeftArm(g);
            drawRightArm(g);
            drawLeftLeg(g);
        }
        else if (hangmanLogic.getRemainingGuesses() == 0) {
            drawHead(g);
            drawBody(g);
            drawLeftArm(g);
            drawRightArm(g);
            drawLeftLeg(g);
            drawRightLeg(g);
        }
    }
}
