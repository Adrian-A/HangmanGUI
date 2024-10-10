import java.util.ArrayList;

/**
 * Handles operations related to the logic behind the GUI and game
 * Provides method to get the word and the correct and wrong characters
 *
 * @author Adrian Alvarez
 */
public class HangmanLogic {
    /**
     * The word that needs to be guessed
     */
    private String wordToGuess;
    /**
     * Amount of guesses that remain for the player
     */
    private int remainingGuesses;
    /**
     * List of correct letters guessed
     */
    private ArrayList<Character> correctChars;
    /**
     * List of incorrect letters guessed
     */
    private ArrayList<Character> incorrectChars; // initialized to null
                                                  // needs to be an ArrayList because the size will change

    // Constructor

    /**
     * Constructor for the HangmanLogic class
     */
    public HangmanLogic(){
        this.remainingGuesses = 6;
        incorrectChars = new ArrayList<Character>();
        correctChars = new ArrayList<Character>();
    }

    // getter/setter

    /**
     * Gets the hangman word to be guessed
     *
     * @return The hangman word to be guessed
     */
    public String getWordToGuess(){
        return wordToGuess;
    }

    /**
     * Sets the hangman word to be guessed
     *
     * @param wtGuess The hangman word to be guessed
     */
    public void setWordToGuess(String wtGuess) {
        this.wordToGuess = wtGuess;
    }

    // getter

    /**
     * Gets the number of letters in the word
     *
     * @return The number of letters in the word
     */
    public int getNumOfChar(){
        return getWordToGuess().length();
    }

    // getter and setter

    /**
     * Gets the remaining amount of guesses the player has
     *
     * @return The remaining amount of guesses
     */
    public int getRemainingGuesses() {
        return remainingGuesses;
    }

    /**
     * Sets the remaining amount of guesses remaining
     *
     * @param num The remaining amount of guesses minus a number
     */
    public void setRemainingGuesses(int num){
        this.remainingGuesses -= num; // remainingGuesses = remainingGuesses - one
    }

    // getter
    /**
     * Gets the correct amount of characters entered
     *
     * @return The correct amount of characters entered
     */
    public ArrayList<Character> getCorrectChars(){
        return correctChars;
    }

    // getter

    /**
     * Gets the incorrect amount of characters entered
     *
     * @return The incorrect amount of characters entered
     */
    public ArrayList<Character> getIncorrectChars(){
        return incorrectChars;
    }

    // determines if word inputted is valid

    /**
     * Determines if the word entered is valid
     *
     * @param word The word the player inputted
     * @return True if the word is a letter, capitalized, and one word and if the user didn't enter nothing
     */
    public boolean wordIsValid(String word){
        return (!(isNotAplha(word) || word.isEmpty() || word.contains(" ")) && allUpper(word));
    }

    /**
     * Determines if the word entered is not a letter
     *
     * @param input The word the player inputted
     * @return True if the word is not a letter
     */
    public boolean isNotAplha(String input){
        char [] chars = input.toCharArray();

        for (char c : chars){
            if(!Character.isLetter(c)){
                return true;
            }
        }
        return false;
    }

    // determines if the word is all upper case

    /**
     * Determines if the word is all upper case
     *
     * @param input The word the player inputted
     * @return True if the word is all upper case
     */
    static boolean allUpper(String input){
        return input.equals(input.toUpperCase());
    }

    // makes sure the character entered is valid

    /**
     * Determines if the letter entered is valid
     *
     * @param ch The letter the player inputted
     * @return True if the letter is a letter and is not already in the incorrect letters list
     */
    public boolean charIsValid(char ch) {
        return (Character.isLetter(ch) & !getIncorrectChars().contains(ch));
    }

    // runs all logistics if the letter is wrongly guessed
    /**
     * Runa the logistics if the letter inputted is wrongly guessed
     *
     * @param incorrectLetter The wrong letter the player inputted
     */
    public void incorrectGuess(char incorrectLetter){
        if (!getIncorrectChars().contains(incorrectLetter)){
            getIncorrectChars().add(incorrectLetter);
        }

        if (getRemainingGuesses() > 0){
            setRemainingGuesses(1);
        }
    }

    /**
     * Determines the total amount of correct letters in the word to be word
     *
     * @return The number of non-repeated letters in the word to be guessed
     */
    public int totalCorrectChars(){
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        ArrayList<Character> tempArray = new ArrayList<>();

        for (char c : alphabet){
            for (int i = 0; i < getNumOfChar(); i++) {
                if (getWordToGuess().charAt(i) == c && !(tempArray.contains(c))) {
                    tempArray.add(c);
                }
            }
        }
        return tempArray.size();
    }
}
