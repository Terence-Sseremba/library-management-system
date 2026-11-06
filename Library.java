// Library.java
// OWNER: Person 3
// Job: holds the array of books and the borrow/return logic.

public class Library {

    private Book[] books = new Book[3]; // array stores our data (3 sample books)
    private Member member;              // the registered member

    // Constructor: fills the array with hardcoded sample books
    public Library() {
        books[0] = new Book("Mastering A-Level Physics", "Various Authors", 20);
        books[1] = new Book("Mastering A-Level Chemistry", "Various Authors", 15);
        books[2] = new Book("Introduction to Java", "Various Authors", 8);
    }

    public void registerMember(Member member) {
        this.member = member;
        System.out.println("Registered: " + member.getName()
                + " (" + member.getRegNumber() + ")");
    }

    public int getBookCount() { return books.length; }

    // Prints every book with how many copies are available
    public void showBooks() {
        System.out.println("\n--- Books in the library ---");
        for (int i = 0; i < books.length; i++) {
            System.out.println((i + 1) + ". " + books[i].getTitle()
                    + " | Available: " + books[i].getAvailableCopies());
        }
    }

    public void borrowBook(int choice) {
        Book book = books[choice - 1]; // user sees 1-3, the array uses 0-2
        if (book.borrow()) {
            System.out.println(member.getName() + " borrowed: " + book.getTitle());
        } else {
            System.out.println("Sorry, no copies of " + book.getTitle() + " left.");
        }
    }

    public void returnBook(int choice) {
        Book book = books[choice - 1];
        if (book.giveBack()) {
            System.out.println("Returned: " + book.getTitle());
        } else {
            System.out.println("All copies are already on the shelf.");
        }
    }
}
