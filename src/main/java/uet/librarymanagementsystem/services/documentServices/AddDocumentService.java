package uet.librarymanagementsystem.services.documentServices;

import uet.librarymanagementsystem.DatabaseOperation.AuthorTable;
import uet.librarymanagementsystem.DatabaseOperation.DocumentDO;
import uet.librarymanagementsystem.DatabaseOperation.TitleTable;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.DocumentFactory;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddDocumentService {
    private static final Logger logger = Logger.getLogger(AddDocumentService.class.getName());

    public void addTitleAndAuthor(String title, String author) throws SQLException {
        // Kiểm tra và thêm Title nếu chưa có
        TitleTable titleTable = new TitleTable();
        TitleTable.createTitleTable();
        TitleTable.insertTitle(title);

        // Kiểm tra và thêm Author nếu chưa có
        AuthorTable authorTable = new AuthorTable();
        AuthorTable.createAuthorTable();
        AuthorTable.insertAuthor(author);
    }

    public void addDocument(String title, String author, String material, String category, String isbn) {
        try {
            // Khởi tạo đối tượng Document từ DocumentFactory
            Document document = DocumentFactory.createDocument(null, title, author, material, category, isbn);

            // Tự động khởi tạo ID và thêm Document vào bảng Document
            addTitleAndAuthor(title, author);// thêm title và author nếu chưa có*/

            document.setId(); // Giả sử phương thức setId() tạo ID mới cho tài liệu dựa trên loại tài liệu và tiêu đề
            System.out.println(document.getId());
            DocumentDO.insertDocument(document);

            System.out.println("Document has been added successfully.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occurred while adding the document", e);
        }
    }
}