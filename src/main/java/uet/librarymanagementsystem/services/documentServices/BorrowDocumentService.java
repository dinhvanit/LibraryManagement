package uet.librarymanagementsystem.services.documentServices;

import uet.librarymanagementsystem.entity.documents.Document;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BorrowDocumentService {
    public Connection getConn() {
        return conn;
    }

    private Connection conn;

    public BorrowDocumentService(Connection connection) {
        this.conn = connection;
    }


}
