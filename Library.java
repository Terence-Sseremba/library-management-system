// Holds the sample books and coordinates loans with the active member.
public class Library {
    private Book[] books = new Book[3];
    private Member member;

    public Library() {
        books[0] = new Book("Mastering A-Level Physics", "Walugada Ronald", 20);
        books[1] = new Book("Mastering A-Level Chemistry", "Walugada Ronald", 15);
        books[2] = new Book("Introduction to Java", "Mrs.Lillian Muyama", 8);
    }

    public boolean registerMember(Member newMember) {
        if (newMember == null) {
            System.out.println("Please enter member details before registering.");
            return false;
        }
        if (member != null && member.getBorrowedCount() > 0) {
            System.out.println("Return all borrowed books before registering another member.");
            return false;
        }
        member = newMember;
        System.out.println("Registered: " + member.getName());
        return true;
    }

    public boolean hasMember() { return member != null; }
    public Member getMember() { return member; }
    public int getBorrowedCount() { return member == null ? 0 : member.getBorrowedCount(); }
    public int getBookCount() { return books.length; }
    public Book getBook(int choice) {
        return choice >= 1 && choice <= books.length ? books[choice - 1] : null;
    }
    public boolean hasReachedBorrowLimit() {
        return member != null && member.getBorrowedCount() >= member.getBorrowLimit();
    }

    public void showBooks() {
        System.out.println("\n--- Books in the library ---");
        for (int i = 0; i < books.length; i++) {
            System.out.println((i + 1) + ". " + books[i].getTitle()
                    + " | Available: " + books[i].getAvailableCopies());
        }
    }

    public void showBorrowedBooks() {
        if (!hasMember() || member.getBorrowedCount() == 0) {
            System.out.println("You have no books to return.");
            return;
        }
        System.out.println("\n--- Your borrowed books ---");
        for (int i = 0; i < books.length; i++) {
            if (member.hasBorrowed(books[i])) {
                System.out.println((i + 1) + ". " + books[i].getTitle());
            }
        }
    }

    public boolean borrowBook(int choice) {
        if (!hasMember()) {
            System.out.println("Please register a member before borrowing.");
            return false;
        }
        if (!validChoice(choice)) return false;
        if (hasReachedBorrowLimit()) {
            System.out.println("Borrowing limit reached (" + member.getBorrowLimit() + " books).");
            return false;
        }

        Book book = books[choice - 1];
        // One active member may borrow each title only once.
        if (member.hasBorrowedTitle(book)) {
            System.out.println("You have already borrowed this title.");
            return false;
        }
        if (!book.borrow()) {
            System.out.println("Sorry, no copies of " + book.getTitle() + " are available.");
            return false;
        }
        if (!member.recordBorrowing(book)) {
            book.giveBack(); // Keep inventory consistent if recording ever fails.
            System.out.println("Could not record the borrowing.");
            return false;
        }
        System.out.println(member.getName() + " borrowed: " + book.getTitle());
        showBorrowedCount();
        return true;
    }

    public boolean returnBook(int choice) {
        if (!hasMember()) {
            System.out.println("Please register a member before returning.");
            return false;
        }
        if (!validChoice(choice)) return false;

        Book book = books[choice - 1];
        if (!member.hasBorrowed(book)) {
            System.out.println("You have not borrowed this book.");
            return false;
        }
        if (!book.giveBack()) {
            System.out.println("Could not return this book; inventory is already full.");
            return false;
        }
        member.removeBorrowing(book);
        System.out.println("Returned: " + book.getTitle());
        showBorrowedCount();
        return true;
    }

    private boolean validChoice(int choice) {
        if (choice >= 1 && choice <= books.length) return true;
        System.out.println("Choose a book number from 1 to " + books.length + ".");
        return false;
    }

    private void showBorrowedCount() {
        System.out.println("Borrowed books: " + member.getBorrowedCount()
                + "/" + member.getBorrowLimit());
    }
}
