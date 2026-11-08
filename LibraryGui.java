import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

public class LibraryGui extends JFrame {
    private final Library library = new Library();
    private final CardLayout sections = new CardLayout();
    private final JPanel content = new JPanel(sections);
    private final JLabel memberLabel = new JLabel("Active member: none");
    private final JTextField nameField = new JTextField(20);
    private final JTextField studentNumberField = new JTextField(20);
    private final JTextField registrationNumberField = new JTextField(20);
    private final JTextField staffNumberField = new JTextField(20);
    private final JTextField departmentField = new JTextField(20);
    private final JComboBox<String> memberType = new JComboBox<>(new String[] {"Student", "Staff"});
    private final CardLayout memberFields = new CardLayout();
    private final JPanel memberDetails = new JPanel(memberFields);
    private final DefaultListModel<String> availableBooks = new DefaultListModel<>();
    private final DefaultListModel<String> borrowedBooks = new DefaultListModel<>();
    private final DefaultListModel<String> allBooks = new DefaultListModel<>();
    private final JList<String> borrowList = new JList<>(availableBooks);
    private final JList<String> returnList = new JList<>(borrowedBooks);
    private int[] borrowedBookNumbers = new int[0];

    public LibraryGui() {
        super("University Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(740, 460);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("University Library Management System");
        title.setBorder(BorderFactory.createEmptyBorder(16, 18, 12, 18));
        title.setFont(title.getFont().deriveFont(20f));
        add(title, BorderLayout.NORTH);

        JPanel navigation = new JPanel();
        navigation.setLayout(new BoxLayout(navigation, BoxLayout.Y_AXIS));
        navigation.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        addNavigationButton(navigation, "Register Member", "register");
        addNavigationButton(navigation, "Borrow Book", "borrow");
        addNavigationButton(navigation, "Return Book", "return");
        addNavigationButton(navigation, "View All Books", "books");
        navigation.add(Box.createVerticalStrut(18));
        JButton exit = new JButton("Exit");
        exit.setAlignmentX(LEFT_ALIGNMENT);
        exit.addActionListener(event -> dispose());
        navigation.add(exit);
        add(navigation, BorderLayout.WEST);

        content.add(createRegistrationPanel(), "register");
        content.add(createBookPanel("Available books", borrowList, "Borrow selected book",
                () -> borrowSelectedBook()), "borrow");
        content.add(createBookPanel("Your borrowed books", returnList, "Return selected book",
                () -> returnSelectedBook()), "return");
        JList<String> allList = new JList<>(allBooks);
        content.add(createBookPanel("All books", allList, null, null), "books");
        content.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 18));
        add(content, BorderLayout.CENTER);

        memberLabel.setBorder(BorderFactory.createEmptyBorder(8, 18, 12, 18));
        add(memberLabel, BorderLayout.SOUTH);
        refreshBooks();
        sections.show(content, "register");
    }

    private void addNavigationButton(JPanel navigation, String label, String section) {
        JButton button = new JButton(label);
        button.setAlignmentX(LEFT_ALIGNMENT);
        button.addActionListener(event -> {
            refreshBooks();
            sections.show(content, section);
        });
        navigation.add(button);
        navigation.add(Box.createVerticalStrut(8));
    }

    private JPanel createRegistrationPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 12));

        JPanel fields = new JPanel(new GridLayout(0, 2, 8, 10));
        fields.add(new JLabel("Member type:"));
        fields.add(memberType);
        fields.add(new JLabel("Name:"));
        fields.add(nameField);

        JPanel studentFields = new JPanel(new GridLayout(2, 2, 8, 10));
        studentFields.add(new JLabel("Student number:"));
        studentFields.add(studentNumberField);
        studentFields.add(new JLabel("Registration number:"));
        studentFields.add(registrationNumberField);

        JPanel staffFields = new JPanel(new GridLayout(2, 2, 8, 10));
        staffFields.add(new JLabel("Staff number:"));
        staffFields.add(staffNumberField);
        staffFields.add(new JLabel("Department:"));
        staffFields.add(departmentField);

        memberDetails.add(studentFields, "Student");
        memberDetails.add(staffFields, "Staff");
        memberType.addActionListener(event ->
                memberFields.show(memberDetails, (String) memberType.getSelectedItem()));

        JPanel form = new JPanel(new BorderLayout(8, 10));
        form.setBorder(BorderFactory.createTitledBorder("Register a student or staff member"));
        form.add(fields, BorderLayout.NORTH);
        form.add(memberDetails, BorderLayout.CENTER);
        panel.add(form, BorderLayout.NORTH);

        JButton register = new JButton("Register Member");
        register.addActionListener(event -> registerMember());
        JPanel actions = new JPanel(new BorderLayout());
        actions.add(register, BorderLayout.WEST);
        panel.add(actions, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createBookPanel(String heading, JList<String> list, String buttonText,
            Runnable action) {
        JPanel panel = new JPanel(new BorderLayout(8, 10));
        panel.add(new JLabel(heading), BorderLayout.NORTH);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        if (buttonText != null) {
            JButton button = new JButton(buttonText);
            button.addActionListener(event -> action.run());
            JPanel actions = new JPanel(new BorderLayout());
            actions.add(button, BorderLayout.WEST);
            panel.add(actions, BorderLayout.SOUTH);
        }
        return panel;
    }

    private void registerMember() {
        String name = nameField.getText().trim();
        boolean student = memberType.getSelectedItem().equals("Student");
        String number = student ? studentNumberField.getText().trim()
                : staffNumberField.getText().trim();
        String detail = student ? registrationNumberField.getText().trim()
                : departmentField.getText().trim();
        if (name.isEmpty() || number.isEmpty() || detail.isEmpty()) {
            showMessage("Complete all member details.", true);
            return;
        }
        Member member = student ? new StudentMember(name, number, detail)
                : new StaffMember(name, number, detail);
        if (library.registerMember(member)) {
            refreshBooks();
            showMessage("Registered " + name + " successfully.", false);
        } else {
            showMessage("Return all borrowed books before registering another member.", true);
        }
    }

    private void borrowSelectedBook() {
        int index = borrowList.getSelectedIndex();
        if (!library.hasMember()) {
            showMessage("Please register a member before borrowing.", true);
        } else if (index < 0) {
            showMessage("Select a book to borrow.", true);
        } else {
            Book book = library.getBook(index + 1);
            if (library.borrowBook(index + 1)) {
                refreshBooks();
                showMessage("Borrowed: " + book.getTitle(), false);
            } else if (library.hasReachedBorrowLimit()) {
                showMessage("Borrowing limit reached (" + library.getMember().getBorrowLimit()
                        + " books).", true);
            } else if (library.getMember().hasBorrowedTitle(book)) {
                showMessage("You have already borrowed this title.", true);
            } else {
                showMessage("No copies of this book are available.", true);
            }
        }
    }

    private void returnSelectedBook() {
        int index = returnList.getSelectedIndex();
        if (!library.hasMember()) {
            showMessage("Please register a member before returning.", true);
        } else if (index < 0) {
            showMessage("Select one of your borrowed books to return.", true);
        } else {
            int bookNumber = borrowedBookNumbers[index];
            String title = library.getBook(bookNumber).getTitle();
            if (library.returnBook(bookNumber)) {
                refreshBooks();
                showMessage("Returned: " + title, false);
            } else {
                showMessage("This book could not be returned.", true);
            }
        }
    }

    private void refreshBooks() {
        availableBooks.clear();
        borrowedBooks.clear();
        allBooks.clear();
        int[] numbers = new int[library.getBookCount()];
        int borrowed = 0;
        for (int number = 1; number <= library.getBookCount(); number++) {
            Book book = library.getBook(number);
            String description = book.getTitle() + " — " + book.getAuthor()
                    + " | Available: " + book.getAvailableCopies();
            availableBooks.addElement(description);
            allBooks.addElement(description);
            if (library.hasMember() && library.getMember().hasBorrowed(book)) {
                borrowedBooks.addElement(book.getTitle());
                numbers[borrowed++] = number;
            }
        }
        borrowedBookNumbers = new int[borrowed];
        System.arraycopy(numbers, 0, borrowedBookNumbers, 0, borrowed);
        Member member = library.getMember();
        memberLabel.setText(member == null ? "Active member: none"
                : "Active member: " + member.getName() + " | Borrowed: "
                + member.getBorrowedCount() + "/" + member.getBorrowLimit());
    }

    private void showMessage(String message, boolean error) {
        JOptionPane.showMessageDialog(this, message, error ? "Action unavailable" : "Success",
                error ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryGui().setVisible(true));
    }
}
