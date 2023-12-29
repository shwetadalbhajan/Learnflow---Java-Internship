import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankingDashboardUI {
    private JFrame dashboardFrame;
    private BankingFunctions atmFunctions;
    private String userId;

    public BankingDashboardUI(BankingFunctions atmFunctions, String userId) {
        this.atmFunctions = atmFunctions;
        this.userId = userId;

        dashboardFrame = new JFrame("ATM Dashboard - Welcome " + userId);

        JButton transactionHistoryButton = new JButton("Transaction History");
        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JButton transferButton = new JButton("Transfer");
        JButton logoutButton = new JButton("Logout");

        transactionHistoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayMessage(atmFunctions.getTransactionHistory(userId));
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double withdrawalAmount = getAmountInput("Enter the withdrawal amount:");
                if (atmFunctions.withdraw(userId, withdrawalAmount)) {
                    displayMessage("Withdrawal successful.");
                } else {
                    displayMessage("Insufficient balance.");
                }
            }
        });

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double depositAmount = getAmountInput("Enter the deposit amount:");
                atmFunctions.deposit(userId, depositAmount);
                displayMessage("Deposit successful.");
            }
        });

        transferButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double transferAmount = getAmountInput("Enter the transfer amount:");
                String recipientAccount = getInput("Enter the recipient's account number:");
                if (atmFunctions.transfer(userId, transferAmount, recipientAccount)) {
                    displayMessage("Transfer successful.");
                } else {
                    displayMessage("Transfer failed. Check recipient's account number.");
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dashboardFrame.dispose();
                new BankingUI(); // Open the main menu again
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));
        panel.add(transactionHistoryButton);
        panel.add(withdrawButton);
        panel.add(depositButton);
        panel.add(transferButton);
        panel.add(logoutButton);

        dashboardFrame.getContentPane().add(panel);
        dashboardFrame.setSize(400, 300);
        dashboardFrame.setLocationRelativeTo(null); // Center the frame
        dashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dashboardFrame.setVisible(true);
    }

    private double getAmountInput(String message) {
        String amountString = getInput(message);
        try {
            return Double.parseDouble(amountString);
        } catch (NumberFormatException e) {
            displayMessage("Invalid amount. Please enter a valid number.");
            return 0.0;
        }
    }

    private String getInput(String message) {
        return JOptionPane.showInputDialog(dashboardFrame, message);
    }

    private void displayMessage(String message) {
        JOptionPane.showMessageDialog(dashboardFrame, message);
    }
}
