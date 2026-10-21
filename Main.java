import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class LibraryTransaction {
    private final String bookId;
    private final String bookName;
    private final String userName;
    private final String issueDate;
    private final String returnDate;

    LibraryTransaction(String bookId, String bookName, String userName,
                      String issueDate, String returnDate) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.userName = userName;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
    }

    String toFileRecord() {
        return String.join("|", bookId, bookName, userName, issueDate, returnDate);
    }

    static LibraryTransaction fromFileRecord(String line) {
        String[] fields = line.split("\\|", -1);
        if (fields.length != 5) {
            return null;
        }

        for (int index = 0; index < fields.length; index++) {
            fields[index] = fields[index].trim();
            if (fields[index].isEmpty()) {
                return null;
            }
        }

        return new LibraryTransaction(
                fields[0], fields[1], fields[2], fields[3], fields[4]);
    }

    @Override
    public String toString() {
        return "Book ID: " + bookId
                + " | Book Name: " + bookName
                + " | Student/User Name: " + userName
                + " | Issue Date: " + issueDate
                + " | Return Date: " + returnDate;
    }
}

public class Main {
    private static final String TRANSACTION_FILE = "transactions.txt";
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Library Transaction Manager");

        while (true) {
            displayMenu();
            String choice = readLine("Enter your choice: ");

            if (choice == null) {
                System.out.println("Input ended. Exiting.");
                return;
            }

            switch (choice.trim()) {
                case "1":
                    recordTransaction();
                    break;
                case "2":
                    viewRecords();
                    break;
                case "3":
                    System.out.println("Exiting library transaction manager.");
                    return;
                default:
                    System.out.println("Invalid menu choice. Please enter 1, 2, or 3.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println();
        System.out.println("1. Record transaction");
        System.out.println("2. View records");
        System.out.println("3. Exit");
    }

    private static void recordTransaction() {
        System.out.println("Enter transaction details. Every field is required.");

        String bookId = readRequiredField("Book ID: ");
        if (bookId == null) {
            return;
        }

        String bookName = readRequiredField("Book Name: ");
        if (bookName == null) {
            return;
        }

        String userName = readRequiredField("Student/User Name: ");
        if (userName == null) {
            return;
        }

        String issueDate = readRequiredField("Issue Date: ");
        if (issueDate == null) {
            return;
        }

        String returnDate = readRequiredField("Return Date: ");
        if (returnDate == null) {
            return;
        }

        LibraryTransaction transaction = new LibraryTransaction(
                bookId, bookName, userName, issueDate, returnDate);

        try (FileWriter writer = new FileWriter(TRANSACTION_FILE, true)) {
            writer.write(transaction.toFileRecord());
            writer.write(System.lineSeparator());
            System.out.println("Transaction saved successfully to " + TRANSACTION_FILE + ".");
        } catch (IOException exception) {
            System.out.println("Unable to save the transaction to " + TRANSACTION_FILE
                    + ": " + exception.getMessage());
        }
    }

    private static void viewRecords() {
        File transactionFile = new File(TRANSACTION_FILE);

        if (!transactionFile.exists()) {
            System.out.println("No transaction file found at '" + TRANSACTION_FILE
                    + "'. Record a transaction first.");
            return;
        }

        if (transactionFile.length() == 0) {
            System.out.println("The transaction file is empty. No records to display.");
            return;
        }

        int displayedRecords = 0;
        int lineNumber = 0;

        try (FileReader fileReader = new FileReader(transactionFile);
             BufferedReader reader = new BufferedReader(fileReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.trim().isEmpty()) {
                    continue;
                }

                LibraryTransaction transaction = LibraryTransaction.fromFileRecord(line);
                if (transaction == null) {
                    System.out.println("Skipping incomplete or malformed record on line "
                            + lineNumber + ".");
                    continue;
                }

                if (displayedRecords == 0) {
                    System.out.println("Transaction records:");
                }

                displayedRecords++;
                System.out.println(displayedRecords + ". " + transaction);
            }
        } catch (IOException exception) {
            System.out.println("Unable to read " + TRANSACTION_FILE + ": "
                    + exception.getMessage());
            return;
        }

        if (displayedRecords == 0) {
            System.out.println("No valid transaction records found in " + TRANSACTION_FILE + ".");
        }
    }

    private static String readRequiredField(String prompt) {
        String value = readLine(prompt);
        if (value == null) {
            System.out.println("Input ended before all fields were entered. Transaction not saved.");
            return null;
        }

        value = value.trim();
        if (value.isEmpty()) {
            System.out.println("A required field was blank. Transaction not saved.");
            return null;
        }

        if (value.contains("|")) {
            System.out.println("A field cannot contain the '|' character. Transaction not saved.");
            return null;
        }

        return value;
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        if (!SCANNER.hasNextLine()) {
            return null;
        }
        return SCANNER.nextLine();
    }
}
