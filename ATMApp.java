import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }
}

class ATM {
    private BankAccount account;

    public ATM(BankAccount account) {
        this.account = account;
    }

    public boolean withdraw(double amount) {
        if (account.withdraw(amount)) {
            return true;
        } else {
            return false;
        }
    }

    public void deposit(double amount) {
        account.deposit(amount);
    }

    public double checkBalance() {
        return account.getBalance();
    }
}

class ATMGUI extends JFrame implements ActionListener {
    private ATM atm;
    private JTextField amountField;
    private JLabel balanceLabel, messageLabel;

    public ATMGUI(ATM atm) {
        this.atm = atm;

        setTitle("ATM Machine");
        setSize(500, 400);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(45, 45, 45), 0, getHeight(), new Color(0, 0, 128));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(10, 10));
        
        JPanel northPanel = new JPanel();
        northPanel.setBackground(new Color(0, 0, 128));
        balanceLabel = new JLabel("Balance: $" + atm.checkBalance());
        balanceLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        balanceLabel.setForeground(Color.WHITE);
        northPanel.add(balanceLabel);
        contentPane.add(northPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 2, 10, 10));
        centerPanel.setBackground(new Color(0, 0, 128));
        JLabel amountLabel = new JLabel("Enter Amount: ");
        amountLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        amountLabel.setForeground(Color.WHITE);
        centerPanel.add(amountLabel);
        
        amountField = new JTextField();
        amountField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        centerPanel.add(amountField);

        JButton depositButton = createStyledButton("Deposit");
        JButton withdrawButton = createStyledButton("Withdraw");
        JButton checkBalanceButton = createStyledButton("Check Balance");
        centerPanel.add(depositButton);
        centerPanel.add(withdrawButton);
        centerPanel.add(checkBalanceButton);
        contentPane.add(centerPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setBackground(new Color(0, 0, 128));
        messageLabel = new JLabel("Welcome to the ATM!");
        messageLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        messageLabel.setForeground(Color.WHITE);
        southPanel.add(messageLabel);
        contentPane.add(southPanel, BorderLayout.SOUTH);

        depositButton.addActionListener(this);
        withdrawButton.addActionListener(this);
        checkBalanceButton.addActionListener(this);
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
        button.setToolTipText(text + " money");
        button.setContentAreaFilled(false);
        button.setOpaque(true);

        // Add hover effect
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

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        try {
            double amount;
            switch (action) {
                case "Deposit":
                    amount = Double.parseDouble(amountField.getText());
                    if (amount <= 0) {
                        messageLabel.setText("Please enter a positive amount.");
                        return;
                    }
                    atm.deposit(amount);
                    messageLabel.setText("Deposited: Rs" + amount);
                    break;

                case "Withdraw":
                    amount = Double.parseDouble(amountField.getText());
                    if (amount <= 0) {
                        messageLabel.setText("Please enter a positive amount.");
                        return;
                    }
                    if (atm.withdraw(amount)) {
                        messageLabel.setText("Withdrew: Rs" + amount);
                    } else {
                        messageLabel.setText("Insufficient balance for withdrawal.");
                    }
                    break;

                case "Check Balance":
                    messageLabel.setText("Your current balance is: Rs" + atm.checkBalance());
                    break;
            }

            balanceLabel.setText("Balance: Rs" + atm.checkBalance());
        } catch (NumberFormatException ex) {
            messageLabel.setText("Please enter a valid number.");
        }
        amountField.setText("");
    }
}

public class ATMApp {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(10000); 
        ATM atm = new ATM(account); 
        new ATMGUI(atm); 
    }
}
