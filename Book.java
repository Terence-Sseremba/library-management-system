// Book.java
// OWNER: Elvis Otaremwa
// Job: represents ONE book and knows how to be borrowed and returned.

public class Book {

    // private = only this class can touch these directly (encapsulation)
    private String title;
    private String author;
    private int totalCopies;      // how many copies the library owns
    private int availableCopies;  // how many are on the shelf right now

    // Constructor: runs when we write new Book(...)
    public Book(String title, String author, int totalCopies) {
        this.title = title;                 // 'this.title' = the field, 'title' = the parameter
        this.author = author;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies; // at the start, every copy is on the shelf
    }

    // Getters: let other classes READ the private fields
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getAvailableCopies() { return availableCopies; }

    // Returns true if borrowing worked, false if no copies are left
    public boolean borrow() {
        if (availableCopies > 0) {
            availableCopies--;   // one copy leaves the shelf
            return true;
        }
        return false;
    }

    // Returns true if the return worked. We can't have more than the library owns
    public boolean giveBack() {
        if (availableCopies < totalCopies) {
            availableCopies++;   // one copy comes back
            return true;
        }
        return false;
    }
}
