package uet.librarymanagementsystem.entity.users;

import uet.librarymanagementsystem.entity.documents.Document;

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
}
