package librarymanagementsystem;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class Book {
    private String title;
    private String author;
    private String ISBN;
    private boolean isAvailable;

    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.isAvailable = true;
    }

    public String getTitle(){
        return title;
    }
public String getAuthor(){
        return author;
    }
public String getISBN(){
        return ISBN;
    }

    public boolean isAvailable(){
        return isAvailable;
    }

    public void setAvailability(boolean available){
        this.isAvailable = available;
    }

    public String toString() {
        return title + " by " + author + " (ISBN: " + ISBN + ")";
    }
}


abstract class Member {
    protected String name;
    protected String memberId;
    protected List<Book> borrowedBooks;

    public Member(String name, String memberId){
        this.name = name;
        this.memberId = memberId;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getMemberId() {
        return memberId;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public abstract boolean borrowBook(Book book);

    public void returnBook(Book book) {
        if (borrowedBooks.contains(book)) {
            borrowedBooks.remove(book);
            book.setAvailability(true);
        }
    }

   
    public String toString() {
        return name + " (ID: " + memberId + ")";
    }
}


class Student extends Member {
    private final int MAX_BOOKS = 5;

    public Student(String name, String memberId) {
        super(name, memberId);
    }

   
    public boolean borrowBook(Book book) {
        if (borrowedBooks.size() < MAX_BOOKS && book.isAvailable()) {
            borrowedBooks.add(book);
            book.setAvailability(false);
            return true;
        }
        return false;
    }
}


class Teacher extends Member {
    private final int MAX_BOOKS = 10;

    public Teacher(String name, String memberId) {
        super(name, memberId);
    }


    public boolean borrowBook(Book book) {
        if (borrowedBooks.size() < MAX_BOOKS && book.isAvailable()) {
            borrowedBooks.add(book);
            book.setAvailability(false);
            return true;
        }
        return false;
    }
}
class Library {
    private List<Book> books;
    private List<Member> members;

    public Library() {
        books = new ArrayList<>();
        members = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public Book searchBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    public boolean issueBook(Member member, String bookTitle) {
        Book book = searchBookByTitle(bookTitle);
        if (book != null && book.isAvailable()) {
            return member.borrowBook(book);
        }
        return false;
    }

    public void returnBook(Member member, String bookTitle) {
        Book book = searchBookByTitle(bookTitle);
        if (book != null) {
            member.returnBook(book);
        }
    }

    public void displayBooks() {
        for (Book book : books) {
            System.out.println(book);
        }
    }
}



public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();

        library.addBook(new Book("Amar Ekushe", "Mohammad Nurul Huda", "BD001"));
        
        
   library.addBook(new Book("Bangladesh: A Legacy of Blood", "Anthony Mascarenhas", "BD002"));
    library.addBook(new Book("Agni Balaka", "Kazi Nazrul Islam", "BD003"));
        library.addBook(new Book("Lal Shalu", "Syed Waliullah", "BD004"));
        
        library.addBook(new Book("Padma Nadir Majhi", "Manik Bandopadhyay", "BD005"));


       
     Member student = new Student("Ayesha", "S001");
        Member teacher = new Teacher("Professor Rahim", "T001");

        library.addMember(student);
        library.addMember(teacher);

   
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nLibrary System Menu:");
            System.out.println("1. Display all books");
            System.out.println("2. Issue a book");
            System.out.println("3. Return a book");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();  

            switch (option) {
                case 1:
                    System.out.println("\nBooks in the library:");
                    library.displayBooks();
                    break;
                    

                case 2:
                    System.out.print("\nEnter member ID: ");
                    String memberId = scanner.nextLine();
                    System.out.print("Enter book title: ");
                    String bookTitle = scanner.nextLine();

                    
                    
                    Member member = memberId.equals("S001") ? student : teacher;
                    if (library.issueBook(member, bookTitle)) {
                        System.out.println("Book issued successfully to " + member.getName());
                    } else {
                        System.out.println("Could not issue book. Either book is not available or limit exceeded.");
                    }
                    break;
                    

                case 3:
                    System.out.print("\nEnter member ID: ");
                    memberId = scanner.nextLine();
                    System.out.print("Enter book title: ");
                    bookTitle = scanner.nextLine();

                    member = memberId.equals("S001") ? student : teacher;
                    library.returnBook(member, bookTitle);
                    System.out.println("Book returned successfully by " + member.getName());
                    break;

                    
                case 4:
                    running = false;
                    System.out.println("Exiting the system...");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        }

        scanner.close();
    }
}
