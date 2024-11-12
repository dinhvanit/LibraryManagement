package uet.librarymanagementsystem.entity.transactions;

import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.users.Student;

public class Transaction {
    private String id;
    private Document document;
    private Student student;
    private String borrowDate;
    private String returnDate;
    private String dueDate;

    public Transaction(Document document, Student student, String borrowDate, String returnDate, String dueDate) {
        this.document = document;
        this.student = student;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.dueDate= dueDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public String getDueDate() {
        return dueDate;
    }
}
