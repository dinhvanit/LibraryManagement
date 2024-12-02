package uet.librarymanagementsystem.entity.users;

import uet.librarymanagementsystem.entity.documents.Document;

import java.util.ArrayList;
import java.util.List;

public class Admin extends User {

    // Constructor
    public Admin(String id, String name, String dateOfBirth, String phoneNumber, String email, String password) {
        super(id, name, dateOfBirth, phoneNumber, email, password);
    }
}
