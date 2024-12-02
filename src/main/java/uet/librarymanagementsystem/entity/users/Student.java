package uet.librarymanagementsystem.entity.users;

import uet.librarymanagementsystem.entity.documents.Document;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    // Constructor
    public Student(String id, String name, String dateOfBirth, String phoneNumber, String email, String password) {
        super(id, name, dateOfBirth, phoneNumber, email, password);
    }

    public Student() {
        super();
    }
}
