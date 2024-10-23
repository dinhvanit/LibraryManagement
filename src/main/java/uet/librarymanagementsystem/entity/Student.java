package uet.librarymanagementsystem.entity;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {

    private List<Document> borrowedDocuments;

    // Constructor
    public Student(int id, String name, String dateOfBirth, String phoneNumber, String email, String password) {
        super(id, name, dateOfBirth, phoneNumber, email, password);
        this.borrowedDocuments = new ArrayList<>();
    }

    // Tìm tài liệu
    public void searchDocument(List<Document> documents, String title) {
        for (Document document : documents) {
            if (document.getTitle().equalsIgnoreCase(title)) {
                System.out.println("Document found: " + document);
                return;
            }
        }
        System.out.println("Document not found.");
    }

    // Mượn tài liệu
    public void borrowDocument(Document document) {
        if (document.getQuantity() > 0) {
            borrowedDocuments.add(document);
            document.setQuantity(document.getQuantity() - 1);
            System.out.println("Document borrowed successfully.");
        } else {
            System.out.println("Document is not available.");
        }
    }

    // Trả tài liệu
    public void returnDocument(Document document) {
        if (borrowedDocuments.contains(document)) {
            borrowedDocuments.remove(document);
            document.setQuantity(document.getQuantity() + 1);
            System.out.println("Document returned successfully.");
        } else {
            System.out.println("You haven't borrowed this document.");
        }
    }
}
