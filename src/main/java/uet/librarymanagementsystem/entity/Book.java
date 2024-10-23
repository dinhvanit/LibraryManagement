package uet.librarymanagementsystem.entity;

public class Book extends Document {
    private String genre;
    private String isbn;

    /**
     * Constructor 1.
     *
     * @param id          ma sach
     * @param title       ten sach
     * @param author      tac gia
     * @param quantity    so luong
     * @param genre       the loai
     * @param isbn        ma vach
     */
    public Book(String id, String title, String author, String category, int quantity, String genre, String isbn) {
        super(id, title, author, category, quantity);
        this.genre = genre;
        this.isbn = isbn;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * In ra ID, ISBN, title, author, quantity, available.
     */
    @Override
    public void printInfo() {
        System.out.println("ID: " + getId());
        System.out.println("ISBN: " + isbn);
        System.out.println("Title: " + getTitle());
        System.out.println("Author: " + getAuthor());
        System.out.println("Quantity: " + getQuantity());
        System.out.println("Available: " + getAvailable());
    }
}
