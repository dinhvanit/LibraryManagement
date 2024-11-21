package uet.librarymanagementsystem.entity.documents.materials;

import javafx.collections.ObservableList;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.MaterialType;
import uet.librarymanagementsystem.services.documentServices.SearchDocumentService;

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

    public String getQuantityCode() {
        String quantityCode = null;
        SearchDocumentService searchDocumentService = new SearchDocumentService();
        try {
            ObservableList<Document> listDocument = searchDocumentService.searchAll(
                    title, author, getMaterial(), getCategory());
            String Max = "0000";
            for (Document document : listDocument) {
                if (document.getId().substring(12, 16).compareTo(Max) > 0) {
                    System.out.println("YES");
                    Max = document.getId().substring(12, 16);
                } else {
                    System.out.println("NO");
                    //return String.format("%04d", i + 1);
                }
                //System.out.println(document.getId().substring(0).getClass().getName());
            }
            int maxNumber = Integer.parseInt(Max);
            maxNumber += 1;

            return String.format("%04d", maxNumber);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setId() {
        this.id = MaterialType.JOURNAL.getCode()
                + Journal.JournalCategory.valueOf(String.valueOf(category)).getCode()
                + getTitleCode() + getAuthorCode() + getQuantityCode();
    }

    public enum JournalCategory {
        RESEARCH("01"),
        REVIEW("02"),
        CASE_STUDY("03"), // tại sao cái này bị lỗi substring
        OTHERS ("04"); // Thêm các loại category cho tạp chí

        private final String code;

        JournalCategory(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}
