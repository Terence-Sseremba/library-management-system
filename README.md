# University Library Management System

This is a Java-based Library Management System we developed as part of our university Object-Oriented Programming (OOP) assignment.

We started with a simple console application where users could register members and manage books. As we continued working on the project, we added borrowing and returning rules, different member types, and later built a graphical interface using Java Swing.

The project now has two ways of running the same library system: through the console or through the Swing GUI.

## What the System Can Do

- Register students and staff members.
- Borrow and return books.
- Display the books available in the library.
- Keep track of borrowed books for the active member.
- Update available book copies when books are borrowed or returned.
- Prevent members from borrowing the same title twice.
- Check borrowing limits based on the member type.
- Handle invalid user input and actions.
- Run through either the console or the Swing GUI.

### Borrowing Rules

| Member Type | Borrowing Limit |
|---|---|
| Student | 2 books |
| Staff | 3 books |

For now, the system works with one active member at a time. Before registering another member, the current member must return all borrowed books.

We've kept this approach simple because the project is mainly focused on demonstrating Java OOP concepts.

## OOP Concepts Used

Throughout the project, we've applied some of the main concepts we've been learning in Java.

**Encapsulation:** Classes such as `Book` and `Member` keep their fields private and provide methods for accessing or updating the information.

**Inheritance:** `StudentMember` and `StaffMember` extend the abstract `Member` class. This allows them to share common member details while keeping their own information.

**Abstraction:** The `Member` class defines common behaviour, including the abstract `getBorrowLimit()` method that each member type must implement.

**Polymorphism:** The `Library` class uses `getBorrowLimit()` to check how many books a member can borrow. This allows the same borrowing logic to work for both students and staff.

We've also used methods to separate different tasks, making the code easier to read and reducing repeated logic.

## Project Files

| File | Purpose |
|---|---|
| `Book.java` | Stores book details and manages available copies |
| `Member.java` | Abstract parent class for members and their borrowing records |
| `StudentMember.java` | Student details and the two-book borrowing limit |
| `StaffMember.java` | Staff details and the three-book borrowing limit |
| `Library.java` | Handles member registration, borrowing, returns, and book inventory |
| `LibraryApp.java` | Runs the console application |
| `LibraryGui.java` | Runs the Java Swing graphical interface |

## How to Run the Project

You'll need Java installed on your computer. We developed and tested the project using Java 21.

First, clone the repository:

```bash
git clone https://github.com/Terence-Sseremba/library-management-system.git
```

Open the project folder:

```bash
cd library-management-system
```

Compile the Java files:

```bash
javac *.java
```

### Option 1: Run the Console Application

```bash
java LibraryApp
```

The console displays a menu where you can register a member, borrow books, return books, view available books, or exit.

### Option 2: Run the Swing GUI

```bash
java LibraryGui
```

This opens the graphical version of the application.

From there, you can use buttons and forms to register members, borrow and return books, and view the library's book list.

## How We Developed the Project

We worked on the project in stages so that we could understand and test each part before moving forward.

### Version 1: The Foundation

We started by bringing together the main Java classes contributed by our team members.

This gave us the basic structure for representing books, members, and the library.

### Version 2: Console Application

We added a menu-driven system with student and staff registration, borrowing limits, book returns, and input validation.

This was also where we worked more on inheritance, abstraction, and polymorphism.

### Version 3: Java Swing GUI

After testing the console application, we introduced a graphical interface using Java Swing.

We kept the existing library classes and connected them to the GUI, allowing users to perform the same actions through buttons and forms.

The console application is still available.

## Current Limitations

The project currently uses three sample book titles and supports one active member at a time.

Book records and borrowing information are kept in memory while the application runs. They aren't saved permanently when the program closes.

The console and GUI also start their own library instances, so they don't share a running session.

These are the current limits of our assignment project, and they leave room for improvements in the future.

## Testing

During development, we compiled the Java classes and tested member registration, borrowing limits, book returns, inventory updates, and invalid actions.

We also tested the Swing GUI and confirmed that the console application still works after adding the graphical interface.

## Final Note

This project gave us an opportunity to put what we've been learning in Object-Oriented Programming into practice.

Working together as a team, reviewing each other's contributions, and improving the application step by step helped us understand how different Java classes can work together in one program.

We may continue improving the system in the future, but for now, we've completed the console and Swing versions planned for this assignment.
