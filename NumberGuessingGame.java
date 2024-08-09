import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

class NumberGuessingGame extends JFrame implements ActionListener {
    private int randomNumber, attempts, maxAttempts, round, score;
    private JTextField guessField;
    private JLabel feedbackLabel, attemptsLabel, scoreLabel, roundLabel;
    private JButton guessButton, playAgainButton;

    public NumberGuessingGame() {
        maxAttempts = 10;
        round = 1;
        score = 0;

        setTitle("Number Guessing Game");
        setSize(500, 400);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(85, 85, 255), 0, getHeight(), new Color(45, 45, 128));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(10, 10));

        JPanel northPanel = new JPanel();
        northPanel.setBackground(new Color(45, 45, 128));
        roundLabel = createStyledLabel("Round: " + round);
        scoreLabel = createStyledLabel("Score: " + score);
        northPanel.add(roundLabel);
        northPanel.add(scoreLabel);
        contentPane.add(northPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(4, 1, 10, 10));
        centerPanel.setBackground(new Color(45, 45, 128));

        JLabel promptLabel = new JLabel("Guess a number between 1 and 100:");
        promptLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        promptLabel.setForeground(Color.WHITE);
        centerPanel.add(promptLabel);

        guessField = new JTextField();
        guessField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        centerPanel.add(guessField);

        feedbackLabel = createStyledLabel("");
        centerPanel.add(feedbackLabel);

        attemptsLabel = createStyledLabel("Attempts Left: " + maxAttempts);
        centerPanel.add(attemptsLabel);

        contentPane.add(centerPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setBackground(new Color(45, 45, 128));

        guessButton = createStyledButton("Guess");
        southPanel.add(guessButton);

        playAgainButton = createStyledButton("Play Again");
        playAgainButton.setEnabled(false);
        southPanel.add(playAgainButton);

        contentPane.add(southPanel, BorderLayout.SOUTH);

        guessButton.addActionListener(this);
        playAgainButton.addActionListener(this);
        startNewRound();
        setVisible(true);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        return label;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setBackground(new Color(30, 144, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setToolTipText(text);
        button.setContentAreaFilled(false);
        button.setOpaque(true);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 191, 255));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(30, 144, 255));
            }
        });

        return button;
    }

    private void startNewRound() {
        randomNumber = new Random().nextInt(100) + 1;
        attempts = 0;
        attemptsLabel.setText("Attempts Left: " + maxAttempts);
        feedbackLabel.setText("");
        guessField.setText("");
        guessField.setEnabled(true);
        guessButton.setEnabled(true);
        playAgainButton.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (action.equals("Guess")) {
            try {
                int guess = Integer.parseInt(guessField.getText());
                attempts++;
                int attemptsLeft = maxAttempts - attempts;

                if (guess == randomNumber) {
                    feedbackLabel.setText("Correct! You've guessed the number.");
                    score += attemptsLeft;
                    guessField.setEnabled(false);
                    guessButton.setEnabled(false);
                    playAgainButton.setEnabled(true);
                } else if (guess < randomNumber) {
                    feedbackLabel.setText("Too low! Try again.");
                } else {
                    feedbackLabel.setText("Too high! Try again.");
                }

                attemptsLabel.setText("Attempts Left: " + attemptsLeft);

                if (attemptsLeft == 0 && guess != randomNumber) {
                    feedbackLabel.setText("Out of attempts! The number was " + randomNumber);
                    guessField.setEnabled(false);
                    guessButton.setEnabled(false);
                    playAgainButton.setEnabled(true);
                }

            } catch (NumberFormatException ex) {
                feedbackLabel.setText("Please enter a valid number.");
            }
        } else if (action.equals("Play Again")) {
            round++;
            roundLabel.setText("Round: " + round);
            scoreLabel.setText("Score: " + score);
            startNewRound();
        }
    }

    public static void main(String[] args) {
        new NumberGuessingGame();
    }
}
