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
    private String reviewDate;
    private String rating;
    private String review;

    public Transaction(Document document, Student student,
                       String borrowDate, String returnDate, String dueDate) {
        this.document = document;
        this.student = student;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.dueDate= dueDate;
    }

    public Transaction(String id, Document document, Student student,
                       String borrowDate, String returnDate, String dueDate,
                       String reviewDate, String rating, String review) {
        this.id = id;
        this.document = document;
        this.student = student;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.dueDate = dueDate;
        this.reviewDate = reviewDate;
        this.rating = rating;
        this.review = review;
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

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
