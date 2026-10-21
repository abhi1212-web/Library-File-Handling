# Java Library File Handling

Practicum 12: a standalone, package-free Java program for recording and
viewing library transactions in a local text file.

## Compile and run

Run these commands from this project directory:

```bash
javac Main.java
java Main
```

The program writes records to `transactions.txt` in the current working
directory. The file is created automatically when the first transaction is
saved. It is a local runtime data file, not a source file, and each new
transaction is appended to the end of it.

## Transaction fields

Every `LibraryTransaction` contains:

- Book ID
- Book Name
- Student/User Name
- Issue Date
- Return Date

Records are stored as one pipe-separated line in this order. The program
rejects blank fields and the `|` character so that records can be read back
reliably.

## Menu

1. **Record transaction** - enter all five fields and append the transaction
   to `transactions.txt`.
2. **View records** - read and display saved transactions.
3. **Exit** - close the program.

## Concepts demonstrated

- Defining a `LibraryTransaction` class with final fields and a constructor
- Using `FileWriter` in append mode to preserve existing records
- Using `FileReader` together with `BufferedReader` to read records line by line
- Using try-with-resources for both writing and reading
- Handling a missing file, an empty file, malformed records, incomplete input,
  invalid menu choices, end-of-input, and I/O failures with clear messages
- Keeping runtime data in a clearly named local text file

## Sample behavior

```text
Library Transaction Manager

1. Record transaction
2. View records
3. Exit
Enter your choice: 2
No transaction file found at 'transactions.txt'. Record a transaction first.

1. Record transaction
2. View records
3. Exit
Enter your choice: 1
Enter transaction details. Every field is required.
Book ID: B-101
Book Name: Java Fundamentals
Student/User Name: Asha Sharma
Issue Date: 2026-10-21
Return Date: 2026-11-04
Transaction saved successfully to transactions.txt.

1. Record transaction
2. View records
3. Exit
Enter your choice: 2
Transaction records:
1. Book ID: B-101 | Book Name: Java Fundamentals | Student/User Name: Asha Sharma | Issue Date: 2026-10-21 | Return Date: 2026-11-04

1. Record transaction
2. View records
3. Exit
Enter your choice: 9
Invalid menu choice. Please enter 1, 2, or 3.
```
