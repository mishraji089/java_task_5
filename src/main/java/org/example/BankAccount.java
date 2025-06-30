import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class Transaction {
    private String type;
    private double amount;
    private double balanceAfter;
    private String timestamp;

    public Transaction(String type, double amount, double balanceAfter) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String toString() {
        return String.format("%s: %s $%.2f, Balance: $%.2f", timestamp, type, amount, balanceAfter);
    }
}


class Account {
    private String accountNumber;
    private double balance;
    private List<Transaction> transactionHistory;

    public Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        if (initialBalance > 0) {
            transactionHistory.add(new Transaction("Initial Deposit", initialBalance, balance));
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive");
            return;
        }
        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount, balance));
        System.out.printf("Deposited $%.2f. New balance: $%.2f%n", amount, balance);
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive");
            return false;
        }
        if (amount > balance) {
            System.out.println("Insufficient funds");
            return false;
        }
        balance -= amount;
        transactionHistory.add(new Transaction("Withdrawal", amount, balance));
        System.out.printf("Withdrew $%.2f. New balance: $%.2f%n", amount, balance);
        return true;
    }

    public void printTransactionHistory() {
        System.out.println("\nTransaction History for Account " + accountNumber + ":");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions recorded.");
        } else {
            for (Transaction transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }
}


public class BankAccount {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get initial balance from user
        System.out.print("Enter initial balance for account: $");
        double initialBalance;
        while (true) {
            try {
                initialBalance = Double.parseDouble(scanner.nextLine());
                if (initialBalance < 0) {
                    System.out.print("Initial balance cannot be negative. Please enter a valid amount: $");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a numeric value: $");
            }
        }

        
        Account account = new Account("1234567890", initialBalance);
        System.out.println("Account created with Account Number: " + account.getAccountNumber());

        
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. View Transaction History");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");

            String choice;
            try {
                choice = scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                continue;
            }

            if (choice.equals("1")) {
                System.out.print("Enter deposit amount: $");
                try {
                    double amount = Double.parseDouble(scanner.nextLine());
                    account.deposit(amount);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid amount. Please enter a numeric value.");
                }
            } else if (choice.equals("2")) {
                System.out.print("Enter withdrawal amount: $");
                try {
                    double amount = Double.parseDouble(scanner.nextLine());
                    account.withdraw(amount);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid amount. Please enter a numeric value.");
                }
            } else if (choice.equals("3")) {
                account.printTransactionHistory();
            } else if (choice.equals("4")) {
                System.out.println("Exiting program.");
                break;
            } else {
                System.out.println("Invalid choice. Please select 1, 2, 3, or 4.");
            }

            
            System.out.printf("Current Balance: $%.2f%n", account.getBalance());
        }

        scanner.close();
    }
}
