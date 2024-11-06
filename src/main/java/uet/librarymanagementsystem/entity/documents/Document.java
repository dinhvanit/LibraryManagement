package uet.librarymanagementsystem.entity.documents;

import javafx.collections.ObservableList;
import uet.librarymanagementsystem.DatabaseOperation.DatabaseManager;
import uet.librarymanagementsystem.services.documentServices.SearchDocument;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class Document {
    protected String title;
    protected String author;
    protected String id; // ID sẽ được tạo từ hàm trong DatabaseDO
    protected Connection conn = DatabaseManager.connect();

    public Document(String title, String author) {
        this.title = title;
        this.author = author;
        this.id = ""; // Khởi tạo ID là chuỗi rỗng
    }

    public Document(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getId() {
        return id;
    }

    public String getTitleCode() {
        String titleCode = null;
        String query = "SELECT ID FROM TITLE WHERE NAME = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                titleCode = String.format("%04d", rs.getInt("ID"));
            } else {
                System.out.println("Không tìm thấy tiêu đề với tên: " + title);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return titleCode;
    }

    public String getAuthorCode() {
        String authorCode = null;
        String query = "SELECT ID FROM AUTHOR WHERE NAME = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, author);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                authorCode = String.format("%04d", rs.getInt("ID"));
            } else {
                System.out.println("Không tìm thấy tác giả với tên: " + author);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authorCode;
    }

    public abstract String getQuantityCode();
    // Phương thức trừu tượng để lấy loại tài liệu
    public abstract String getMaterial();

    // Phương thức trừu tượng để lấy thể loại tài liệu
    public abstract String getCategory();

    // Phương thức trừu tượng để tạo id
    public abstract void setId();
}
