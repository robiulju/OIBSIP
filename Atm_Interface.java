import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Atm_Interface {
    public static void main(String[] args) {
        // Sample user data (replace with actual user data from a database)
        User user = new User("Robiul", "1234", 1000.0);

        // Authenticate user
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        System.out.print("Enter User PIN: ");
        String userPin = scanner.nextLine();

        if (userId.equals(user.getUserId()) && userPin.equals(user.getUserPin())) {
            System.out.println("Authentication successful!");
            ATMOperations atmOperations = new ATMOperations(user);

            int choice;
            do {
                displayMainMenu();
                choice = ATMUtil.readIntegerInput("Enter your choice: ");
                switch (choice) {
                    case 1:
                        atmOperations.checkBalance();
                        break;
                    case 2:
                        atmOperations.getTransactionHistory();
                        break;
                    case 3:
                        atmOperations.withdraw();
                        break;
                    case 4:
                        atmOperations.deposit();
                        break;
                    case 5:
                        atmOperations.transfer();
                        break;
                    case 6:
                        System.out.println("Thank you for using the ATM. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 5);
        } else {
            System.out.println("Authentication failed. Invalid User ID or PIN.");
        }
    }

    private static void displayMainMenu() {
        System.out.println("\nATM Main Menu");
        System.out.println("1. View Current Balance");
        System.out.println("2. View Transactions History");
        System.out.println("3. Withdraw");
        System.out.println("4. Deposit");
        System.out.println("5. Transfer");
        System.out.println("6. Quit");

        
    }
}

class User {
   
    private String userId;
    private String userPin;
    private double balance;

    public User(String userId, String userPin, double balance) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = balance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPin() {
        return userPin;
    }

    public void setUserPin(String userPin) {
        this.userPin = userPin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    // Constructor, getters, and setters
}

class Transaction {
    private String date;
    private String type;
    private double amount;


    public Transaction(String type, double amount) {
        this.date = getCurrentDate(); // You can implement this method to get the current date
        this.type = type;
        this.amount = amount;
    }

    private String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return currentDate.format(formatter);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    // Constructor, getters, and setters
}

class ATMOperations {
    private User user;
    private List<Transaction> transactionHistory;

    public ATMOperations(User user) {
        this.user = user;
        this.transactionHistory = new ArrayList<>();
    }

    public void withdraw() {
        Scanner scanner = new Scanner(System.in);
        double amountToWithdraw;
        do {
            System.out.print("Enter the amount to withdraw: ");
            amountToWithdraw = scanner.nextDouble();
            if (amountToWithdraw <= 0) {
                System.out.println("Invalid amount. Please enter a positive amount.");
            } else if (amountToWithdraw > user.getBalance()) {
                System.out.println("Insufficient funds. You can't withdraw more than your account balance.");
            } else {
                user.setBalance(user.getBalance() - amountToWithdraw);
                Transaction transaction = new Transaction("Withdraw", amountToWithdraw);
                transactionHistory.add(transaction);
                System.out.println("Successfully withdrawn $" + amountToWithdraw);
                break;
            }
        } while (true);
    }

    public void deposit() {
        Scanner scanner = new Scanner(System.in);
        double amountToDeposit;
        do {
            System.out.print("Enter the amount to deposit: ");
            amountToDeposit = scanner.nextDouble();
            if (amountToDeposit <= 0) {
                System.out.println("Invalid amount. Please enter a positive amount.");
            } else {
                user.setBalance(user.getBalance() + amountToDeposit);
                Transaction transaction = new Transaction("Deposit", amountToDeposit);
                transactionHistory.add(transaction);
                System.out.println("Successfully deposited $" + amountToDeposit);
                break;
            }
        } while (true);
    }

    public void transfer() {
        Scanner scanner = new Scanner(System.in);
        double amountToTransfer;
        do {
            System.out.print("Enter the amount to transfer: ");
            amountToTransfer = scanner.nextDouble();
            if (amountToTransfer <= 0) {
                System.out.println("Invalid amount. Please enter a positive amount.");
            } else if (amountToTransfer > user.getBalance()) {
                System.out.println("Insufficient funds. You can't transfer more than your account balance.");
            } else {
                System.out.print("Enter the recipient's User ID: ");
                String recipientUserId = scanner.next();
                // Perform recipient user validation here (not implemented in this basic example)
                // If recipient user is valid, proceed with the transfer
                user.setBalance(user.getBalance() - amountToTransfer);
                Transaction transaction = new Transaction("Transfer to " + recipientUserId, amountToTransfer);
                transactionHistory.add(transaction);
                System.out.println("Successfully transferred $" + amountToTransfer + " to " + recipientUserId);
                break;
            }
        } while (true);
    }

    public void getTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Transaction History:");
            for (Transaction transaction : transactionHistory) {
                System.out.println(transaction.getType() + " - $" + transaction.getAmount());
            }
        }
    }

    public void checkBalance(){
        System.out.println("Current Balance $"+user.getBalance());
    }
}

class ATMUtil {
    public static int readIntegerInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Invalid input. Please enter a valid number: ");
        }
        return scanner.nextInt();
    }
}
