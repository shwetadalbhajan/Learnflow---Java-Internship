import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankingFunctions {
    private Map<String, String> userCredentials;
    private Map<String, Double> balances;
    private Map<String, List<String>> transactionHistory;

    public BankingFunctions() {
        this.userCredentials = new HashMap<>();
        this.balances = new HashMap<>();
        this.transactionHistory = new HashMap<>();
        initializeUsers();
    }

    private void initializeUsers() {
        userCredentials.put("123456", "7890");
        balances.put("123456", 1000.0);
        transactionHistory.put("123456", new ArrayList<>());
    }

    public boolean authenticateUser(String userId, char[] userPin) {
        String pin = userCredentials.get(userId);
        return pin != null && pin.equals(new String(userPin));
    }

    public String getTransactionHistory(String userId) {
        List<String> history = transactionHistory.getOrDefault(userId, new ArrayList<>());
        return String.join("\n", history);
    }

    public boolean withdraw(String userId, double amount) {
        if (amount > 0 && balances.containsKey(userId) && balances.get(userId) >= amount) {
            balances.put(userId, balances.get(userId) - amount);
            transactionHistory.get(userId).add("Withdraw: ₹" + amount);
            return true;
        }
        return false;
    }

    public void deposit(String userId, double amount) {
        if (amount > 0) {
            balances.put(userId, balances.getOrDefault(userId, 0.0) + amount);
            transactionHistory.get(userId).add("Deposit: ₹" + amount);
        }
    }

    public boolean transfer(String userId, double amount, String recipientAccount) {
        if (amount > 0 && balances.containsKey(userId) && balances.get(userId) >= amount) {
            balances.put(userId, balances.get(userId) - amount);
            transactionHistory.get(userId).add("Transfer to " + recipientAccount + ": ₹" + amount);

            // For simplicity, the recipient's account is updated in the same way as a deposit
            balances.put(recipientAccount, balances.getOrDefault(recipientAccount, 0.0) + amount);
            transactionHistory.computeIfAbsent(recipientAccount, k -> new ArrayList<>())
                    .add("Transfer from " + userId + ": ₹" + amount);
            return true;
        }
        return false;
    }
}
