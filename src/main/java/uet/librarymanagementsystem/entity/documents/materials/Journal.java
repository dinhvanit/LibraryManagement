package uet.librarymanagementsystem.entity.documents.materials;

import javafx.collections.ObservableList;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.MaterialType;
import uet.librarymanagementsystem.services.documentServices.SearchDocument;

import java.sql.SQLException;

public class Journal extends Document {
    private final JournalCategory category;

    public Journal(String title, String author, JournalCategory category) {
        super(title, author);
        this.category = category;
    }

    public Journal(String id, String title, String author, JournalCategory category) {
        super(id, title, author);
        this.category = category;
    }

    @Override
    public String getMaterial() {
        return MaterialType.JOURNAL.name(); // Trả về tên enum
    }

    @Override
    public String getCategory() {
        return category.name();
    }

    @Override
    public String getQuantityCode() {
        String quantityCode = null;
        SearchDocument searchDocument = new SearchDocument();
        try {
            ObservableList<Document> listDocument = searchDocument.search(
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
        this.id = MaterialType.JOURNAL.getCode()
                + Book.BookCategory.valueOf(String.valueOf(category)).getCode()
                + getTitleCode() + getAuthorCode() + getQuantityCode();
    }

    public enum JournalCategory {
        RESEARCH("01"),
        REVIEW("02"),
        CASE_STUDY("03");
        //OTHERS; // Thêm các loại category cho tạp chí

        private final String code;

        JournalCategory(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}
