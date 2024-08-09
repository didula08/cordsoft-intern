import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class StudentGradeCalculator extends JFrame implements ActionListener {
    private JTextField[] marksFields;
    private JLabel totalMarksLabel, averageLabel, gradeLabel, messageLabel;
    private JButton calculateButton;
    private int numSubjects;

    public StudentGradeCalculator(int numSubjects) {
        this.numSubjects = numSubjects;

        setTitle("Student Grade Calculator");
        setSize(500, 500);
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
        JLabel instructionLabel = new JLabel("Enter marks for each subject (out of 100):");
        instructionLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        instructionLabel.setForeground(Color.WHITE);
        northPanel.add(instructionLabel);
        contentPane.add(northPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(numSubjects, 2, 10, 10));
        centerPanel.setBackground(new Color(45, 45, 128));
        marksFields = new JTextField[numSubjects];
        
        for (int i = 0; i < numSubjects; i++) {
            JLabel subjectLabel = new JLabel("Subject " + (i + 1) + ": ");
            subjectLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
            subjectLabel.setForeground(Color.WHITE);
            centerPanel.add(subjectLabel);

            marksFields[i] = new JTextField();
            marksFields[i].setFont(new Font("SansSerif", Font.PLAIN, 16));
            centerPanel.add(marksFields[i]);
        }
        
        contentPane.add(centerPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(5, 1, 10, 10));
        southPanel.setBackground(new Color(45, 45, 128));

        calculateButton = createStyledButton("Calculate");
        southPanel.add(calculateButton);

        totalMarksLabel = createResultLabel("Total Marks: ");
        averageLabel = createResultLabel("Average Percentage: ");
        gradeLabel = createResultLabel("Grade: ");
        messageLabel = createResultLabel("");

        southPanel.add(totalMarksLabel);
        southPanel.add(averageLabel);
        southPanel.add(gradeLabel);
        southPanel.add(messageLabel);

        contentPane.add(southPanel, BorderLayout.SOUTH);

        calculateButton.addActionListener(this);
        setVisible(true);
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

    private JLabel createResultLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        return label;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (action.equals("Calculate")) {
            try {
                double totalMarks = 0;
                for (JTextField marksField : marksFields) {
                    double marks = Double.parseDouble(marksField.getText());
                    if (marks < 0 || marks > 100) {
                        messageLabel.setText("Please enter valid marks between 0 and 100.");
                        return;
                    }
                    totalMarks += marks;
                }

                double averagePercentage = totalMarks / numSubjects;
                String grade = calculateGrade(averagePercentage);

                totalMarksLabel.setText("Total Marks: " + totalMarks);
                averageLabel.setText("Average Percentage: " + String.format("%.2f", averagePercentage) + "%");
                gradeLabel.setText("Grade: " + grade);
                messageLabel.setText("Calculation completed successfully!");
            } catch (NumberFormatException ex) {
                messageLabel.setText("Please enter valid numerical marks.");
            }
        }
    }

    private String calculateGrade(double averagePercentage) {
        if (averagePercentage >= 75) return "A";
        else if (averagePercentage >= 65) return "B";
        else if (averagePercentage >= 55) return "C";
        else if (averagePercentage >= 35) return "D";
        else return "F";
    }

    public static void main(String[] args) {
        int numSubjects = 5;
        new StudentGradeCalculator(numSubjects);
    }
}
