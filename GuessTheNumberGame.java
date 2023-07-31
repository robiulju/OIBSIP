import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GuessTheNumberGame extends JFrame {

    private int generatedNumber;
    private int attempts;
    private int maxAttempts;
    private int score;

    private JLabel messageLabel;
    private JTextField guessTextField;
    private JButton guessButton;
    private JLabel attemptsLabel;
    private JLabel scoreLabel;

    public GuessTheNumberGame() {
        generatedNumber = generateRandomNumber();
        attempts = 0;
        maxAttempts = 10;   // 10 is set as maximum number of attempts
        score = 0;

        setTitle("Number Guessing Game");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        messageLabel = new JLabel("Guess a number between 1 and 100");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        guessTextField = new JTextField();
        guessTextField.setHorizontalAlignment(SwingConstants.CENTER);

        guessButton = new JButton("Guess");
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });

        attemptsLabel = new JLabel("Attempts: " + attempts);
        attemptsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(messageLabel);
        add(guessTextField);
        add(guessButton);
        add(attemptsLabel);
        add(scoreLabel);

        setVisible(true);
    }

    private int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(100) + 1;
    }

    private void checkGuess() {
        String guessText = guessTextField.getText();
        int guessedNumber;

        try {
            guessedNumber = Integer.parseInt(guessText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input! Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        attempts++;
        attemptsLabel.setText("Attempts: " + attempts);

        if (guessedNumber == generatedNumber) {
            JOptionPane.showMessageDialog(this, "Congratulations! You guessed the number!", "Win", JOptionPane.INFORMATION_MESSAGE);
            score += (maxAttempts - attempts) * 10;
            scoreLabel.setText("Score: " + score);
            resetGame();
        } else if (guessedNumber < generatedNumber) {
            JOptionPane.showMessageDialog(this, "Try again! The number is higher.", "Wrong", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Try again! The number is lower.", "Wrong", JOptionPane.WARNING_MESSAGE);
        }

        if (attempts >= maxAttempts) {
            JOptionPane.showMessageDialog(this, "Game Over! The number was: " + generatedNumber, "Game Over", JOptionPane.INFORMATION_MESSAGE);
            resetGame();
        }
    }

    private void resetGame() {
        generatedNumber = generateRandomNumber();
        attempts = 0;
        attemptsLabel.setText("Attempts: " + attempts);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GuessTheNumberGame();
            }
        });
    }
}
