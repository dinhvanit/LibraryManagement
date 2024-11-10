package uet.librarymanagementsystem.entity.transactions;

import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.users.Student;

public class Transaction {
    private String id;
    private Document document;
    private Student student;
    private TypeTransaction typeTransaction;
    private String dateTransaction;

    public Transaction(Document document, Student student, TypeTransaction typeTransaction, String dateTransaction) {
        this.document = document;
        this.student = student;
        this.typeTransaction = typeTransaction;
        this.dateTransaction = dateTransaction;
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

    public String getTypeTransaction() {
        return typeTransaction.name();
    }

    public void setTypeTransaction(TypeTransaction typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public String getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(String dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public enum TypeTransaction {
        BORROW,
        RETURN;
    }

}
