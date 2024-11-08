package uet.librarymanagementsystem.entity.users;

import uet.librarymanagementsystem.entity.documents.Document;

import java.util.ArrayList;
import java.util.List;

public class Admin extends User {

    private List<Student> students;

    // Constructor
    public Admin(int id, String name, String dateOfBirth, String phoneNumber, String email, String password) {
        super(id, name, dateOfBirth, phoneNumber, email, password);
        this.students = new ArrayList<>();
    }

    // Thêm tài liệu
    public void addDocument(List<Document> documents, Document document) {
        documents.add(document);
        System.out.println("Document added successfully.");
    }

    // Xoá tài liệu
    public void removeDocument(List<Document> documents, Document document) {
        documents.remove(document);
        System.out.println("Document removed successfully.");
    }

    // Thêm student
    public void addStudent(Student student) {
        students.add(student);
        System.out.println("Student added successfully.");
    }

    // Xoá student
    public void removeStudent(Student student) {
        students.remove(student);
        System.out.println("Student removed successfully.");
    }
}