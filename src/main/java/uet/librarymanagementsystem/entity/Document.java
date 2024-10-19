package uet.librarymanagementsystem.entity;

import java.util.ArrayList;
import java.util.List;

public class Document {
    private String id;
    private String title;
    private String author;
    private int quantity;
    private boolean isAvailable;

    /**
     * Constructor 1.
     *
     * @param id          : ma cua tai lieu
     * @param title       : ten cua tai lieu
     * @param author      : tac gia
     * @param quantity    : so luong
     * @param isAvailable : tinh trang con tai lieu hay khong
     */
    public Document(String id, String title, String author, int quantity, boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
        this.isAvailable = isAvailable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    /**
     * In ra id, title, author, available.
     */
    public void printInfo() {
        System.out.println("ID: " + id);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Quantity: " + quantity);
        System.out.println("Available: " + isAvailable);
    }

    /**
     * Day la ham tam thoi de luu tru du lieu, ve sau se thay bang duong dan den database
     *
     * @return document.
     */
    public static List<Document> getAllDocuments() {
        List<Document> documents = new ArrayList<>();
        documents.add(new Document("1", "Java Programming", "Author A", 1, true));
        documents.add(new Document("2", "Database Systems", "Author B", 2, true));
        documents.add(new Document("3", "Web Development", "Author C", 3, true));
        return documents;
    }


    public static Document getDocumentById(String id) {
        // Logic to retrieve the document from a static list or a database
        List<Document> documents = getAllDocuments(); // Get all documents

        for (Document doc : documents) {
            if (doc.getId().equals(id)) {
                return doc;
            }
        }
        return null;
    }
}
