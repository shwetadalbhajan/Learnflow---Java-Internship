import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankingUI {
    private JFrame frame;
    private BankingFunctions functions;

    public BankingUI() {
        frame = new JFrame("Online Banking Application");
        functions = new BankingFunctions();

        // Set the look and feel to Nimbus for a more modern appearance
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        JButton loginButton = new JButton("Login");
        JButton exitButton = new JButton("Exit");

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new BankingLoginUI(functions, frame);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        panel.add(loginButton);
        panel.add(exitButton);

        frame.getContentPane().add(panel);
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    }
}
