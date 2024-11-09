package uet.librarymanagementsystem.entity.documents.materials;

import javafx.collections.ObservableList;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.MaterialType;
import uet.librarymanagementsystem.services.documentServices.SearchDocumentService;

import java.sql.Date;
import java.sql.SQLException;

public class Newspaper extends Document {
    private final NewspaperCategory category;

    public Newspaper(String title, String author, NewspaperCategory category) {
        super(title, author);
        this.category = category;
    }

    public Newspaper(String id, String title, String author, NewspaperCategory category) {
        super(id, title, author);
        this.category = category;
    }


    @Override
    public String getMaterial() {
        return MaterialType.NEWSPAPER.name(); // Trả về tên enum
    }

    @Override
    public String getCategory() {
        return category.name();
    }

    @Override
    public String getQuantityCode() {
        String quantityCode = null;
        SearchDocumentService searchDocumentService = new SearchDocumentService();
        try {
            ObservableList<Document> listDocument = searchDocumentService.search(
                    title, author, getMaterial(), getCategory());
            int len = listDocument.size();
            for (int i = 0; i < len; i++) {
                if (listDocument.get(i).getId().substring(12, 16).equals(String.format("%04d", i + 1))) {
                    System.out.println("YES");
                } else {
                    System.out.println("NO");
                    return String.format("%04d", i + 1);
                }
                System.out.println(listDocument.get(i).getId().substring(0).getClass().getName());
            }
            return String.format("%04d", len + 1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setId() {
        this.id = MaterialType.NEWSPAPER.getCode()
                + Book.BookCategory.valueOf(String.valueOf(category)).getCode()
                + getTitleCode() + getAuthorCode() + getQuantityCode();
    }

    public enum NewspaperCategory {
        WEATHER("01"),
        TECHNOLOGY("02"),
        SPORTS("03"),
        ENTERTAINMENT("04");
        //OTHER; // Thêm các loại category cho báo

        private final String code;

        NewspaperCategory(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}
