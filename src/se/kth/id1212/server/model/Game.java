package se.kth.id1212.server.model;


import se.kth.id1212.server.data.DataHandler;

/**
 * Created by Robin on 2017-11-16.
 */
public class Game {
    private int score;
    private int attempts;
    private int remainingLetters;

    private String correctWord;
    private String censoredWord;

    private char[] censoredLetterArray;

    private boolean started;
    private boolean solved;

    private final DataHandler dataHandler = new DataHandler();

    private final String RULES =
                    "Welcome to Hangman! The rules are simple, a<br> random word will be chosen " +
                    "and your job is to find out which before you get hanged. The task may " +
                    "be simple but the stakes are high! You will be presented with a word " +
                    "represented by one _ for every letter in the word, you will have one " +
                    "guess per letter in the word before you get hanged. So if the word is " +
                    "three letters long, you only have three guesses! If a letter reoccurs " +
                    "in a word you'll get all of them in one guess, and remember that you " +
                    "can guess the entire word at any time!" +
                    "Instructions:\n" +
                    "Start a game by using the START command\n" +
                    "Make a guess using the GUESS command followed by your guess\n" +
                    "Quit the game using the QUIT command\n";

    public String getRules() {
        return RULES;
    }

    public String startGame() {
        correctWord = dataHandler.retrieveWord();
        remainingLetters = correctWord.length();
        attempts = correctWord.length();
        solved = false;
        generateCensoredWord();

        String message = "NEW GAME \t" + censoredWord + "\t\t" + attempts + "\t\t" + score + "\n";

        if (!started) {
            started = true;
            return "User | Word | Attempts | Score\n" + message;
        }
        else return message;
    }

    public String guess(String guess) {
        attempts--;

        if (attempts < 0) {
            return "YOU'RE HANGED! Start a new game to continue playing!";
        }
        else if (attempts == 0) {
            return "YOU GOT HANGED! The word was: " + correctWord + " Score: " + --score + "\n";
        }
        else if (guess != null) {
            if (guess.length() == 1) {
                checkGuess(guess.toUpperCase().charAt(0));
                if (solved)
                    return "CORRECT GUESS! Sought word was " + correctWord + " Score: " + ++score + "\n";
                else if (attempts == 0) {
                    return "YOU GOT HANGED! The word was: " + correctWord + " Score: " + --score + "\n";
                } else {
                    return "GUESS " + guess + "\t\t" + censoredWord + "\t\t" + attempts + "\t\t" + score + "\n";
                }
            } else if (correctWord.equals(guess)) {
                return "CORRECT GUESS! The word was: " + correctWord + " Score: " + ++score + "\n";
            } else {
                return "GUESS " + guess + "\t\t" + censoredWord + "\t\t" + attempts + "\t\t" + score + "\n";
            }
        } else return "GUESS " + "\t\t" + censoredWord + "\t\t" + attempts + "\t\t" + score + "\n";
    }


    private void generateCensoredWord() {
        censoredLetterArray = new char[2*correctWord.length()-1];

        for (int i = 0; i < 2*correctWord.length()-1; i++) {
            if(i%2 == 0) censoredLetterArray[i] = '_';
            else censoredLetterArray[i] = ' ';
        }
        censoredWord = String.valueOf(censoredLetterArray);
    }

    private void checkGuess(char guess) {
        for (int i = 0; i < censoredLetterArray.length; i++) {
            if (i%2==0) {
                if (censoredLetterArray[i] == '_' && correctWord.charAt(i/2) == guess) {
                    censoredLetterArray[i] = guess;
                    censoredWord = String.valueOf(censoredLetterArray);
                    if (--remainingLetters == 0) {
                        solved = true;
                        break;
                    }
                    else solved = false;
                }
            }
        }
    }
}
