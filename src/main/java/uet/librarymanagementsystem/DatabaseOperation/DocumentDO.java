
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

        return DocumentFactory.createDocument(title, author, material, category);
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

    public static void insertDocument(Document document) throws SQLException {
        Connection con = connect();
        if (con == null || con.isClosed()) {
            throw new SQLException("Cannot insert document, connection is closed or invalid.");
        }
        String insertSQL = "INSERT INTO Document (id, title, author, material, category) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = con.prepareStatement(insertSQL);
        preparedStatement.setString(1, document.getId());
        preparedStatement.setString(2, document.getTitle());
        preparedStatement.setString(3, document.getAuthor());
        preparedStatement.setString(4, document.getMaterial());
        preparedStatement.setString(5, document.getCategory());
        preparedStatement.executeUpdate();
        con.close();
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
            document = DocumentFactory.createDocument(
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
            Document document = DocumentFactory.createDocument(
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
//            Scanner scanner = new Scanner(System.in);
//            int soluot = scanner.nextInt();
//            System.out.println("Hay nhap vao thong tin cac quyen sach");
//            for (int i = 0; i < soluot; i++) {
//                Document newDocument = DocumentDO.inputDocumentFromKeyboard();
//                DocumentDO.insertDocument(newDocument);
//            }
//

            List<Document> documents = DocumentDO.getAllDocument();
            documents.forEach(document -> System.out.println(document.getId() + " - " + document.getTitle() + " - " + document.getAuthor() + " - " + document.getMaterial() + " - " + document.getCategory()));


//            List<Document> documents = DocumentDO.getAllDocument();
//            documents.forEach(document -> System.out.println(document.getId() + " - " + document.getTitle() + " - " + document.getAuthor() + " - " + document.getCategory() + " - " + document.getQuantity()));
            DocumentDO.deleteDocument("Hoa 2");

//            Scanner scanner = new Scanner(System.in);
//            DocumentDO.insertDocument(inputDocumentFromKeyboard());
//            System.out.println("Enter document ID to delete: ");
//            String documentId = scanner.nextLine();
            /*
            Document d1 = new Document("D001", "Java Programming", "John Doe", "Book", "Programming", 10);
            Document d2 = new Document("D002", "Data Structures", "Jane Smith", "Thesis", "Computer Science", 5);
            Document d3 = new Document("D003", "Algorithms", "Robert Martin", "Book", "Mathematics", 3);
            Document d4 = new Document("D004", "Clean Code", "Martin Fowler", "Book", "Software Engineering", 10);
            Document d5 = new Document("D005", "The Pragmatic Programmer", "Andrew Hunt", "Newspaper", "Programming", 10);
            Document d6 = new Document("D006", "Design Patterns", "Erich Gamma", "Book", "Software Engineering", 10);
            Document d7 = new Document("D007", "Introduction to Java Programming", "Herbert Schildt", "Book", "Programming", 10);
            Document d8 = new Document("D008", "Effective Java", "Joshua Bloch", "Joshua Bloch", "Programming", 10);
            Document d9 = new Document("D009", "Head First Java", "Kathy Sierra", "Book", "Programming", 10);
            Document d10 = new Document("D0010", "Python Crash Course", "Eric Matthes", "Newspaper", "Programming", 10);

            insertDocument(d1);
            insertDocument(d2);
            insertDocument(d3);
            insertDocument(d4);
            insertDocument(d5);
            insertDocument(d6);
            insertDocument(d7);
            insertDocument(d8);
            insertDocument(d9);
            insertDocument(d10);
            */


//            DocumentDO.deleteDocument(documentId);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An exception occurred", e); // dung thay cho stack trace

        }
    }

}