
package uet.librarymanagementsystem.DatabaseOperation;

import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.DocumentFactory;
import uet.librarymanagementsystem.entity.documents.materials.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DocumentDO extends DatabaseManager {
    private static final Logger logger = Logger.getLogger(DocumentDO.class.getName());

    public static void createDocumentTable() throws SQLException {
        Connection con = connect();
        if (con == null || con.isClosed()) {
            throw new SQLException("Cannot create document, connection is closed or invalid.");
        }
        Statement statement = con.createStatement();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Document (" +
                "id VARCHAR(255) PRIMARY KEY, " +
                "title VARCHAR(255), " +
                "author VARCHAR(255), " +
                "material VARCHAR(255), " +
                "category VARCHAR(255), " +
                "isbn VARCHAR(255) " +
                ")";
        statement.execute(createTableSQL);
        con.close();
    }

    public static void insertDocument(Document document) throws SQLException {
        Connection con = connect();

        if (con == null || con.isClosed()) {
            throw new SQLException("Cannot insert document, connection is closed or invalid.");
        }

        try {
            System.out.println(document.getMaterial() + " ===== " + document.getCategory() + "in add DO");
            // Kiểm tra nếu id của document đã tồn tại trong bảng Document
            String checkDocumentSQL = "SELECT COUNT(*) FROM Document WHERE id = ?";
            PreparedStatement checkStmt = con.prepareStatement(checkDocumentSQL);
            checkStmt.setString(1, document.getId());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();

            // Nếu tài liệu đã tồn tại, không thực hiện thêm
            if (rs.getInt(1) > 0) {
                System.out.println("Document with id " + document.getId() + " already exists. Skipping insertion.");
                return;
            }

            // Chèn tài liệu vào bảng Document nếu chưa tồn tại
            String insertDocumentSQL = "INSERT INTO Document (id, title, author, material, category, isbn) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement documentStmt = con.prepareStatement(insertDocumentSQL);

            // Set giá trị cho các trường
            documentStmt.setString(1, document.getId());
            documentStmt.setString(2, document.getTitle());
            documentStmt.setString(3, document.getAuthor());

            System.out.println(document.getMaterial() + " ===== " + document.getCategory());
            // Set material và category (enum)
            documentStmt.setString(4, document.getMaterial()); // Giả sử material là enum
            documentStmt.setString(5, document.getCategory()); // Giả sử category là enum
            System.out.println(document.getMaterial() + " ===== " + document.getCategory());

            // Set ISBN nếu là Book, nếu không thì set null
            if (document instanceof Book) {
                Book book = (Book) document;
                documentStmt.setString(6, book.getIsbn());
            } else {
                documentStmt.setNull(6, Types.VARCHAR);
            }

            documentStmt.executeUpdate();

            System.out.println("Document added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            con.close();
        }
    }

    public static Document getDocumentById(String documentId) throws SQLException {
        Connection con = connect();
        String selectSQL = "SELECT * FROM Document WHERE id = ?";
        if (con == null || con.isClosed()) {
            throw new SQLException("Cannot get document by id, connection is closed or invalid.");
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
                    resultSet.getString("category"),
                    resultSet.getString("isbn")
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
            System.out.println("Document with id " + documentId + " has been deleted.");
        } else {
            System.out.println("No document found with id " + documentId);
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
                    resultSet.getString("category"),
                    resultSet.getString("isbn")
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

    public static void clearDocumentTable() throws SQLException {
        Connection con = DatabaseManager.connect();
        if (con == null || con.isClosed()) {
            throw new SQLException("Cannot clear document table, connection is closed or invalid.");
        }

        Statement statement = con.createStatement();
        String deleteDataSQL = "DELETE FROM Document";
        statement.executeUpdate(deleteDataSQL);

        System.out.println("All data in Document table has been deleted successfully.");

        con.close(); // Đóng kết nối
    }

    public static void addIdColumnSQLite() throws SQLException {
        Connection con = connect();
        String sql = "ALTER TABLE database_2 ADD COLUMN id TEXT";
        try (Statement statement = con.createStatement()) {
            statement.execute(sql);
            System.out.println("Column 'id' added successfully.");
        }
    }

    public static void createNewDocumentTableSQLite() throws SQLException {
        Connection con = connect();
        String createTableSQL = "CREATE TABLE Document_New (" +
                "id TEXT PRIMARY KEY, " +
                "title TEXT, " +
                "author TEXT, " +
                "material TEXT, " +
                "category TEXT, " +
                "isbn TEXT, " +
                "desc TEXT, " +
                "img TEXT, " +
                "link TEXT, " +
                "pages INTEGER, " +
                "quantity INTEGER)";
        try (Statement statement = con.createStatement()) {
            statement.execute(createTableSQL);
            System.out.println("New table 'Document_New' created successfully.");
        }
    }

    public static void migrateDataToNewTableSQLite() throws SQLException {
        Connection con = connect();
        String migrateDataSQL = "INSERT INTO Document_New (id, title, author, material, category, isbn, desc, img, link, pages, quantity) " +
                "SELECT null, title, author, bookformat, genre, isbn, desc, img, link, pages, quantity FROM database_2";
        try (Statement statement = con.createStatement()) {
            statement.execute(migrateDataSQL);
            System.out.println("Data migrated to 'Document_New' successfully.");
        }
    }


    public static void main(String[] args) {
        try {

//            List<Document> documents = DocumentDO.getAllDocument();
//            documents.forEach(document -> {
//                System.out.print(document.getId() + " - " + document.getTitle() + " - " + document.getAuthor() + " - " + document.getMaterial() + " - " + document.getCategory());
//
//                // Kiểm tra xem tài liệu có phải là Book không để lấy ISBN
//                if (document instanceof Book) {
//                    Book book = (Book) document;
//                    System.out.println(" - ISBN: " + book.getIsbn());
//                } else {
//                    System.out.println();
//                }
//            });
//
//             createDocumentTable();
//             addIdColumnSQLite();
//             createNewDocumentTableSQLite();
//             migrateDataToNewTableSQLite();
                deleteAllDocuments();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An exception occurred", e); // dung thay cho stack trace

        }
    }

}