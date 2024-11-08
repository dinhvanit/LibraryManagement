
package uet.librarymanagementsystem.DatabaseOperation;

import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.DocumentFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DocumentDO extends DatabaseManager {
    private static final Logger logger = Logger.getLogger(DocumentDO.class.getName());

    public static Document inputDocumentFromKeyboard() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter document title: ");
        String title = scanner.nextLine();

        System.out.println("Enter document author: ");
        String author = scanner.nextLine();

        System.out.println("Enter document category: ");
        String category = scanner.nextLine();

        System.out.println("Enter document material: ");
        String material = scanner.nextLine();

        return DocumentFactory.createDocument("1", title, author, material, category);
    }

    public static void createDocumentTable() throws SQLException {
        Connection con = connect();
        if (con == null || con.isClosed()) {
            throw new SQLException("Cannot create document, connection is closed or invalid.");
        }
        Statement statement = con.createStatement();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Document (" +
                "id VARCHAR(10) PRIMARY KEY, " +
                "title VARCHAR(50), " +
                "author VARCHAR(50), " +
                "material VARCHAR(50), " +
                "category VARCHAR(50) " +
                ")";
        statement.execute(createTableSQL);
        con.close();
    }

//    public static void insertDocument(Document document) throws SQLException {
//        Connection con = connect();
//        if (con == null || con.isClosed()) {
//            throw new SQLException("Cannot insert document, connection is closed or invalid.");
//        }
//        String insertSQL = "INSERT INTO Document (id, title, author, material, category) VALUES (?, ?, ?, ?, ?)";
//        PreparedStatement preparedStatement = con.prepareStatement(insertSQL);
//        preparedStatement.setString(1, document.getId());
//        preparedStatement.setString(2, document.getTitle());
//        preparedStatement.setString(3, document.getAuthor());
//        preparedStatement.setString(4, document.getMaterial());
//        preparedStatement.setString(5, document.getCategory());
//        preparedStatement.executeUpdate();
//        con.close();
//    }

    public static void insertDocument(Document document) throws SQLException {
        Connection con = connect();

        if (con == null || con.isClosed()) {
            throw new SQLException("Cannot insert document, connection is closed or invalid.");
        }

        try {
            // Chèn tài liệu vào bảng Document
            String insertDocumentSQL = "INSERT INTO Document (id, title, author, material, category) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement documentStmt = con.prepareStatement(insertDocumentSQL);
            documentStmt.setString(1, document.getId());
            documentStmt.setString(2, document.getTitle());
            documentStmt.setString(3, document.getAuthor());
            documentStmt.setString(4, document.getMaterial());
            documentStmt.setString(5, document.getCategory());
            documentStmt.executeUpdate();

            // Chèn thông tin title vào bảng Title với các cột id và name
            String insertTitleSQL = "INSERT OR IGNORE INTO Title (id, name) VALUES (?, ?)";
            PreparedStatement titleStmt = con.prepareStatement(insertTitleSQL);
            titleStmt.setString(1, document.getId()); // Sử dụng id của Document làm id cho Title
            titleStmt.setString(2, document.getTitle());
            titleStmt.executeUpdate();

            // Chèn thông tin author vào bảng Author với các cột id và name
            String insertAuthorSQL = "INSERT OR IGNORE INTO Author (id, name) VALUES (?, ?)";
            PreparedStatement authorStmt = con.prepareStatement(insertAuthorSQL);
            authorStmt.setString(1, document.getId()); // Sử dụng id của Document làm id cho Author
            authorStmt.setString(2, document.getAuthor());
            authorStmt.executeUpdate();

            System.out.println("Document, Title, and Author added successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Ném ngoại lệ để xử lý bên ngoài nếu cần
        } finally {
            // Đảm bảo đóng kết nối sau khi hoàn thành
            con.close();
        }
    }

    public static Document getDocumentById(String documentId) throws SQLException {
        Connection con = connect();
        String selectSQL = "SELECT * FROM Document WHERE id = ?";
        if (con == null || con.isClosed()) {
            throw new SQLException("Cannot get document by ID, connection is closed or invalid.");
        }
        PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
        preparedStatement.setString(1, documentId);

        ResultSet resultSet = preparedStatement.executeQuery();
        Document document = null;
        if (resultSet.next()) {
            document = DocumentFactory.createDocument("1",
                    resultSet.getString("title"),
                    resultSet.getString("author"),
                    resultSet.getString("material"),
                    resultSet.getString("category")
            );
        } else {
            System.out.println("not found");
        }

        con.close();
        return document;
    }

    public static void deleteDocument(String documentId) throws SQLException {
        Connection con = connect();
        String deleteSQL = "DELETE FROM Document WHERE id = ?";
        if (con == null || con.isClosed()) {
            throw new SQLException("Cannot delete document, connection is closed or invalid.");
        }
        PreparedStatement preparedStatement = con.prepareStatement(deleteSQL);
        preparedStatement.setString(1, documentId);
        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Document with ID " + documentId + " has been deleted.");
        } else {
            System.out.println("No document found with ID " + documentId);
        }

        con.close();
    }

    public static List<Document> getAllDocument() throws SQLException {
        List<Document> documentList = new ArrayList<>();
        Connection con = connect();
        assert con != null;
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Document");
        while (resultSet.next()) {
            Document document = DocumentFactory.createDocument("1",
                    resultSet.getString("title"),
                    resultSet.getString("author"),
                    resultSet.getString("material"),
                    resultSet.getString("category")
            );
            documentList.add(document);
        }
        con.close();
        return documentList;
    }

    public static void deleteAllDocuments() throws SQLException {
        Connection con = connect();
        if (con == null || con.isClosed()) {
            throw new SQLException("Cannot delete all documents, connection is closed or invalid.");
        }
        Statement statement = con.createStatement();
        String deleteSQL = "DELETE FROM Document";
        int rowsAffected = statement.executeUpdate(deleteSQL);
        System.out.println(rowsAffected + " records have been deleted from the Document table.");
        con.close();
    }


    public static void main(String[] args) {
        try {

            List<Document> documents = DocumentDO.getAllDocument();
            documents.forEach(document -> System.out.println(document.getId() + " - " + document.getTitle() + " - " + document.getAuthor() + " - " + document.getMaterial() + " - " + document.getCategory()));


//
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An exception occurred", e); // dung thay cho stack trace

        }
    }

}