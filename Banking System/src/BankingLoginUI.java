import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankingLoginUI {
    private JFrame loginFrame;
    private JTextField userIdField;
    private JPasswordField userPinField;
    private BankingFunctions atmFunctions;

    public BankingLoginUI(BankingFunctions atmFunctions, JFrame parentFrame) {
        this.atmFunctions = atmFunctions;

        loginFrame = new JFrame("Login - Banking Application");

        JLabel userIdLabel = new JLabel("User ID:");
        JLabel userPinLabel = new JLabel("User PIN:");

        userIdField = new JTextField(20);
        userPinField = new JPasswordField(20);

        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userId = userIdField.getText();
                char[] userPin = userPinField.getPassword();
                if (atmFunctions.authenticateUser(userId, userPin)) {
                    loginFrame.dispose(); // Close the login window
                    new BankingDashboardUI(atmFunctions, userId);
                    parentFrame.dispose(); // Close the main menu
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Invalid credentials. Please try again.");
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(userIdLabel);
        panel.add(userIdField);
        panel.add(userPinLabel);
        panel.add(userPinField);
        panel.add(loginButton);

        loginFrame.getContentPane().add(panel);
        loginFrame.setSize(400, 200);
        loginFrame.setLocationRelativeTo(parentFrame); // Center the frame relative to the main menu
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginFrame.setVisible(true);
    }
}
