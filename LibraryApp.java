import java.util.Scanner;

public class LibraryApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Library library = new Library();

        System.out.println("===== WELCOME TO THE LIBRARY =====");

        // Registration is NOT hardcoded: the user types it in
        String name = readText(input, "Enter your name: ");
        String studentNo = readText(input, "Enter your student number: ");
        String regNo = readText(input, "Enter your reg number: ");
        library.registerMember(new Member(name, studentNo, regNo));

        // Borrow
        library.showBooks();
        int choice = readInt(input, "Choose a book to borrow: ", 1, library.getBookCount());
        library.borrowBook(choice);

        // Show updated stock
        library.showBooks();

        // Return
        choice = readInt(input, "Choose a book to return: ", 1, library.getBookCount());
        library.returnBook(choice);
        library.showBooks();

        input.close();
    }

    // Helper: asks a question and keeps asking until the answer is not empty.
    // do-while because we must ask at least once before we can check.
    static String readText(Scanner input, String prompt) {
        String text;
        do {
            System.out.print(prompt);
            text = input.nextLine().trim(); // trim() removes spaces at both ends
            if (text.isEmpty()) System.out.println("This cannot be empty.");
        } while (text.isEmpty());
        return text;
    }

    // Helper: asks until the user enters a whole number between min and max.
    // parseInt throws NumberFormatException on letters, and catch handles it.
    static int readInt(Scanner input, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(input.nextLine().trim());
                if (value >= min && value <= max) return value; // valid: exit the loop
                System.out.println("Enter a number from " + min + " to " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Numbers only please.");
            }
        }
    }
}

    

