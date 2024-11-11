package uet.librarymanagementsystem.services.documentServices;

import uet.librarymanagementsystem.DatabaseOperation.BorrowedDocuments;
import uet.librarymanagementsystem.DatabaseOperation.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;

public class AddBorrowDocumentService {
    public Connection getConn() {
        return conn;
    }

    private Connection conn;

    public AddBorrowDocumentService() {
        this.conn = DatabaseManager.connect();
    }

    public void addBorrowDocument() throws SQLException {
        // ae nao ket hop voi scenebuilder thi lay dau vao tu ban phim
        BorrowedDocuments.insertBorrowedDocument("23020677", "0101000200310001", "2024/11/10", "2024/11/11");
    }

    public static void main(String[] args) throws SQLException {
        AddBorrowDocumentService addBorrowDocumentService = new AddBorrowDocumentService();
        addBorrowDocumentService.addBorrowDocument();

    }

}
