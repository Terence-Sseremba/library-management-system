import java.util.NoSuchElementException;
import java.util.Scanner;

public class LibraryApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Library library = new Library();
        boolean running = true;

        try {
            while (running) {
                System.out.println("\n===== UNIVERSITY LIBRARY MANAGEMENT SYSTEM =====\n");
                System.out.println("1. Register Member");
                System.out.println("2. Borrow Book");
                System.out.println("3. Return Book");
                System.out.println("4. Show All Books");
                System.out.println("5. Exit\n");

                int choice = readInt(input, "Enter your choice: ", 1, 5);
                switch (choice) {
                    case 1:
                        registerMember(input, library);
                        break;
                    case 2:
                        borrowBooks(input, library);
                        break;
                    case 3:
                        returnBooks(input, library);
                        break;
                    case 4:
                        library.showBooks();
                        break;
                    case 5:
                        running = false;
                        System.out.println("Goodbye!");
                        break;
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("\nInput ended. Goodbye!");
        } finally {
            input.close();
        }
    }

    private static void registerMember(Scanner input, Library library) {
        if (library.getBorrowedCount() > 0) {
            System.out.println("Return all borrowed books before registering another member.");
            return;
        }
        System.out.println("\nRegister as:");
        System.out.println("1. Student");
        System.out.println("2. Staff\n");
        int type = readInt(input, "Enter your choice: ", 1, 2);
        String name = readText(input, "Enter your name: ");
        if (type == 1) {
            String studentNo = readText(input, "Enter your student number: ");
            String regNo = readText(input, "Enter your reg number: ");
            library.registerMember(new StudentMember(name, studentNo, regNo));
        } else {
            String staffNo = readText(input, "Enter your staff number: ");
            String department = readText(input, "Enter your department: ");
            library.registerMember(new StaffMember(name, staffNo, department));
        }
    }

    private static void borrowBooks(Scanner input, Library library) {
        if (!library.hasMember()) {
            System.out.println("Please register a member before borrowing.");
            return;
        }
        do {
            if (library.hasReachedBorrowLimit()) {
                System.out.println("Borrowing limit reached. Return a book before borrowing another.");
                return;
            }
            library.showBooks();
            int choice = readInt(input, "Choose a book to borrow: ", 1, library.getBookCount());
            library.borrowBook(choice);
            if (library.hasReachedBorrowLimit()) {
                System.out.println("Borrowing limit reached. Return a book before borrowing another.");
                return;
            }
        } while (readYesNo(input, "Would you like to borrow another book? (yes/no): "));
    }

    private static void returnBooks(Scanner input, Library library) {
        if (!library.hasMember()) {
            System.out.println("Please register a member before returning.");
            return;
        }
        do {
            if (library.getBorrowedCount() == 0) {
                System.out.println("You have no books to return.");
                return;
            }
            library.showBorrowedBooks();
            int choice = readInt(input, "Choose a book to return: ", 1, library.getBookCount());
            library.returnBook(choice);
            if (library.getBorrowedCount() == 0) return;
        } while (readYesNo(input, "Would you like to return another book? (yes/no): "));
    }

    static String readText(Scanner input, String prompt) {
        String text;
        do {
            System.out.print(prompt);
            text = readLine(input).trim();
            if (text.isEmpty()) System.out.println("This cannot be empty.");
        } while (text.isEmpty());
        return text;
    }

    static int readInt(Scanner input, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(readLine(input).trim());
                if (value >= min && value <= max) return value;
                System.out.println("Enter a number from " + min + " to " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Numbers only please.");
            }
        }
    }

    private static boolean readYesNo(Scanner input, String prompt) {
        while (true) {
            String answer = readText(input, prompt);
            if (answer.equalsIgnoreCase("yes")) return true;
            if (answer.equalsIgnoreCase("no")) return false;
            System.out.println("Please answer yes or no.");
        }
    }

    // All prompts use this check so an ended input stream exits the menu safely.
    private static String readLine(Scanner input) {
        if (!input.hasNextLine()) throw new NoSuchElementException();
        return input.nextLine();
    }
}
