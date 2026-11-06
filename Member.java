// Shared details and borrowing records for students and staff.
public abstract class Member {
    private String name;
    private Book[] borrowedBooks = new Book[3]; // The largest limit is three.
    private int borrowedCount;

    public Member(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public int getBorrowedCount() { return borrowedCount; }

    // Each type of member supplies its own limit.
    public abstract int getBorrowLimit();

    public boolean hasBorrowed(Book book) {
        for (int i = 0; i < borrowedCount; i++) {
            if (borrowedBooks[i] == book) return true;
        }
        return false;
    }

    public boolean hasBorrowedTitle(Book book) {
        for (int i = 0; i < borrowedCount; i++) {
            if (borrowedBooks[i].getTitle().equals(book.getTitle())) return true;
        }
        return false;
    }

    // The Library checks the limit and inventory before recording a loan.
    boolean recordBorrowing(Book book) {
        if (book == null || hasBorrowedTitle(book) || borrowedCount >= borrowedBooks.length) return false;
        borrowedBooks[borrowedCount] = book;
        borrowedCount++;
        return true;
    }

    boolean removeBorrowing(Book book) {
        for (int i = 0; i < borrowedCount; i++) {
            if (borrowedBooks[i] == book) {
                // Shift later books left so the active records stay together.
                for (int j = i; j < borrowedCount - 1; j++) {
                    borrowedBooks[j] = borrowedBooks[j + 1];
                }
                borrowedCount--;
                borrowedBooks[borrowedCount] = null;
                return true;
            }
        }
        return false;
    }
}
