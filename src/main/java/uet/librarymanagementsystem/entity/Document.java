package uet.librarymanagementsystem.entity;

import java.util.ArrayList;
import java.util.List;

public class Document {
    private String id;
    private String title;
    private String author;
    private String category;
    private int quantity;
    private boolean isAvailable;

    public Document(String id, String title, String author, String category, int quantity, boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
        System.out.println("Category: " + category);
        System.out.println("Quantity: " + quantity);
        System.out.println("Available: " + isAvailable);
    }

    /*
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

    */
}
