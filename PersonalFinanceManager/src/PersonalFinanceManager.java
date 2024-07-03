import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Transaction {
    String date;
    String category;
    double amount;
    String type; // "income" or "expense"

    public Transaction(String date, String category, double amount, String type) {
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.type = type;
    }

    @Override
    public String toString() {
        return date + "," + category + "," + amount + "," + type;
    }
}

public class PersonalFinanceManager {
    static ArrayList<Transaction> transactions = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static final String FILE_NAME = "transactions.txt";

    public static void main(String[] args) {
        loadTransactions();
        while (true) {
            System.out.println("1. Add Transaction\n2. View Transactions\n3. Generate Report\n4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            switch (choice) {
                case 1:
                    addTransaction();
                    break;
                case 2:
                    viewTransactions();
                    break;
                case 3:
                    generateReport();
                    break;
                case 4:
                    saveTransactions();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void addTransaction() {
        System.out.println("Enter date (YYYY-MM-DD):");
        String date = scanner.nextLine();
        System.out.println("Enter category:");
        String category = scanner.nextLine();
        System.out.println("Enter amount:");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        System.out.println("Enter type (income/expense):");
        String type = scanner.nextLine();
        transactions.add(new Transaction(date, category, amount, type));
        System.out.println("Transaction added successfully.");
    }

    static void viewTransactions() {
        for (Transaction transaction : transactions) {
            System.out.println(transaction.date + " - " + transaction.category + " - " + transaction.amount + " - " + transaction.type);
        }
    }

    static void generateReport() {
        double totalIncome = 0, totalExpense = 0;
        for (Transaction transaction : transactions) {
            if (transaction.type.equals("income")) {
                totalIncome += transaction.amount;
            } else {
                totalExpense += transaction.amount;
            }
        }
        System.out.println("Total Income: " + totalIncome);
        System.out.println("Total Expense: " + totalExpense);
        System.out.println("Net Savings: " + (totalIncome - totalExpense));
    }

    static void loadTransactions() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String date = parts[0];
                    String category = parts[1];
                    double amount = Double.parseDouble(parts[2]);
                    String type = parts[3];
                    transactions.add(new Transaction(date, category, amount, type));
                }
            }
            System.out.println("Transactions loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading transactions: " + e.getMessage());
        }
    }

    static void saveTransactions() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Transaction transaction : transactions) {
                bw.write(transaction.toString());
                bw.newLine();
            }
            System.out.println("Transactions saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving transactions: " + e.getMessage());
        }
    }
}
