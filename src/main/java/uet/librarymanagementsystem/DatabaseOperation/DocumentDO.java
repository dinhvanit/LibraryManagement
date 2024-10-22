package uet.librarymanagementsystem.DatabaseOperation;

import uet.librarymanagementsystem.entity.Document;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DocumentDO extends DatabaseManager{
    private static final Logger logger = Logger.getLogger(DocumentDO.class.getName());
    public static Document inputDocumentFromKeyboard() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter document ID: ");
        String id = scanner.nextLine();

        System.out.println("Enter document title: ");
        String title = scanner.nextLine();

        System.out.println("Enter document author: ");
        String author = scanner.nextLine();

        System.out.println("Enter document category: ");
        String category = scanner.nextLine();

        System.out.println("Enter document quantity: ");
        int quantity = scanner.nextInt();

        return new Document(id, title, author, category, quantity);
    }

    public static void createDocumentTable() throws SQLException {
        Connection con = connect();
        Statement statement = con.createStatement();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Document (" +
                "id VARCHAR(10) PRIMARY KEY, " +
                "title VARCHAR(50), " +
                "author VARCHAR(50), " +
                "category VARCHAR(50), " +
                "quantity INT" +
                ")";
        statement.execute(createTableSQL);
        con.close();
    }

    public static void insertDocument(Document document) throws SQLException {
        Connection con = connect();
        String insertSQL = "INSERT INTO Document (id, title, author, category, quantity) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = con.prepareStatement(insertSQL);
        preparedStatement.setString(1, document.getId());
        preparedStatement.setString(2, document.getTitle());
        preparedStatement.setString(3, document.getAuthor());
        preparedStatement.setString(4, document.getCategory());
        preparedStatement.setInt(5, document.getQuantity());
        preparedStatement.executeUpdate();
        con.close();
    }

    public static void deleteDocument(String documentId) throws SQLException {
        Connection con = connect();
        String deleteSQL = "DELETE FROM Document WHERE id = ?";
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
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Document");
        while (resultSet.next()) {
            Document document = new Document(
                    resultSet.getString("id"),
                    resultSet.getString("title"),
                    resultSet.getString("author"),
                    resultSet.getString("category"),
                    resultSet.getInt("quantity")
            );
            documentList.add(document);
        }
        con.close();
        return documentList;
    }

    // xoá table, sau sẽ cho thêm đầu vào để có thể xoá dữ liệu của cả các class khác.
    // truncate se xoa nhanh hon delete nhung khong backup duoc (tam thoi chua chay duoc)
    public static void truncateDocumentTable() throws SQLException {
        Connection con = connect();
        Statement statement = con.createStatement();
        String truncateSQL = "TRUNCATE TABLE Document";
        statement.execute(truncateSQL);
        System.out.println("All records from the Document table have been truncated.");
        con.close();
    }
    //hien tai dang dung ham xoa du lieu nay
    public static void deleteAllDocuments() throws SQLException {
        Connection con = connect();
        Statement statement = con.createStatement();
        String deleteSQL = "DELETE FROM Document";
        int rowsAffected = statement.executeUpdate(deleteSQL);
        System.out.println(rowsAffected + " records have been deleted from the Document table.");
        con.close();
    }

    public static void main(String[] args) {
        try {
            /*            DocumentDO.createDocumentTable();
            Scanner scanner = new Scanner(System.in);
            int soluot = scanner.nextInt();
            System.out.println("Hay nhap vao thong tin cac quyen sach");
            for (int i = 0; i < soluot; i++) {
                Document newDocument = DocumentDO.inputDocumentFromKeyboard();
                DocumentDO.insertDocument(newDocument);
            }

            List<Document> documents = DocumentDO.getAllDocument();
            documents.forEach(document -> System.out.println(document.getId() + " - " + document.getTitle() + " - " + document.getAuthor() + " - " + document.getCategory() + " - " + document.getQuantity()));
            */
            Scanner scanner = new Scanner(System.in);
            DocumentDO.insertDocument(inputDocumentFromKeyboard());
//            System.out.println("Enter document ID to delete: ");
//            String documentId = scanner.nextLine();
//            DocumentDO.deleteDocument(documentId);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An exception occurred", e); // dung thay cho stack trace

        }
    }

}
