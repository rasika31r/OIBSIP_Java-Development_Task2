import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class NumberGuessingGameSwing extends JFrame implements ActionListener {

    Random rand = new Random();

    int number;
    int attempts = 5;
    int rounds = 3;
    int currentRound = 1;
    int totalScore = 0;

    JLabel titleLabel, roundLabel, infoLabel, attemptsLabel, scoreLabel;
    JTextField inputField;
    JButton guessButton, nextRoundButton;

    Color bgColor = new Color(245, 247, 250);
    Color buttonColor = new Color(52, 152, 219);
    Color successColor = new Color(39, 174, 96);
    Color tooHighColor = new Color(231, 76, 60);
    Color tooLowColor = new Color(52, 152, 219);
    Color textColor = new Color(44, 62, 80);

    public NumberGuessingGameSwing() {
        setTitle("Number Guessing Game");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(bgColor);
        setLayout(new BorderLayout(10, 10));

        // Title
        titleLabel = new JLabel("🎮 NUMBER GUESSING GAME 🎮", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(41, 128, 185));
        add(titleLabel, BorderLayout.NORTH);

        // Center Panel
        JPanel centerPanel = new JPanel(new GridLayout(6, 1, 10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Gradient background effect
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0, Color.WHITE, 0, getHeight(), bgColor));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        centerPanel.setBackground(bgColor);

        roundLabel = new JLabel("Round: 1 / 3", JLabel.CENTER);
        infoLabel = new JLabel("Guess a number between 1 and 100", JLabel.CENTER);
        attemptsLabel = new JLabel("Attempts left: 5", JLabel.CENTER);
        scoreLabel = new JLabel("Score: 0", JLabel.CENTER);

        roundLabel.setFont(new Font("Arial", Font.BOLD, 16));
        infoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        attemptsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));

        roundLabel.setForeground(textColor);
        attemptsLabel.setForeground(textColor);
        scoreLabel.setForeground(textColor);

        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.BOLD, 18));
        inputField.setHorizontalAlignment(JTextField.CENTER);

        centerPanel.add(roundLabel);
        centerPanel.add(infoLabel);
        centerPanel.add(attemptsLabel);
        centerPanel.add(scoreLabel);
        centerPanel.add(new JLabel("Enter your guess:", JLabel.CENTER));
        centerPanel.add(inputField);

        add(centerPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(bgColor);

        guessButton = new JButton("Guess");
        nextRoundButton = new JButton("Next Round");

        guessButton.setBackground(buttonColor);
        guessButton.setForeground(Color.WHITE);
        guessButton.setFocusPainted(false);
        nextRoundButton.setBackground(new Color(46, 204, 113));
        nextRoundButton.setForeground(Color.WHITE);
        nextRoundButton.setFocusPainted(false);

        buttonPanel.add(guessButton);
        buttonPanel.add(nextRoundButton);
        add(buttonPanel, BorderLayout.SOUTH);

        guessButton.addActionListener(this);
        nextRoundButton.addActionListener(this);

        startNewRound();
        setVisible(true);
    }

    void startNewRound() {
        number = rand.nextInt(100) + 1;
        attempts = 5;
        inputField.setText("");
        infoLabel.setText("Guess a number between 1 and 100");
        infoLabel.setForeground(textColor);
        attemptsLabel.setText("Attempts left: " + attempts);
        roundLabel.setText("Round: " + currentRound + " / " + rounds);
        guessButton.setEnabled(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guessButton) {
            try {
                int guess = Integer.parseInt(inputField.getText());
                attempts--;

                if (guess == number) {
                    int score = (attempts + 1) * 10;
                    totalScore += score;
                    scoreLabel.setText("Score: " + totalScore);

                    infoLabel.setText("🎉 Correct Guess!");
                    infoLabel.setForeground(successColor);

                    JOptionPane.showMessageDialog(this,
                            "Correct Guess!\nPoints Earned: " + score);

                    currentRound++;
                    checkGameStatus();
                } else if (guess < number) {
                    infoLabel.setText("⬆ Too Low! Try Higher");
                    infoLabel.setForeground(tooLowColor);
                } else {
                    infoLabel.setText("⬇ Too High! Try Lower");
                    infoLabel.setForeground(tooHighColor);
                }

                attemptsLabel.setText("Attempts left: " + attempts);

                if (attempts == 0) {
                    JOptionPane.showMessageDialog(this,
                            "Out of attempts!\nNumber was: " + number);
                    guessButton.setEnabled(false);
                    currentRound++;
                    checkGameStatus();
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number!");
            }
        }

        if (e.getSource() == nextRoundButton) {
            currentRound++;
            checkGameStatus();
        }
    }

    void checkGameStatus() {
        if (currentRound > rounds) {
            JOptionPane.showMessageDialog(this,
                    "🎉 GAME OVER 🎉\nTotal Score: " + totalScore);
            System.exit(0);
        } else {
            startNewRound();
        }
    }

    public static void main(String[] args) {
        new NumberGuessingGameSwing();
    }
}
