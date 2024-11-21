package uet.librarymanagementsystem.entity.documents.materials;

import javafx.collections.ObservableList;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.MaterialType;
import uet.librarymanagementsystem.services.documentServices.SearchDocumentService;

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
        this.id = MaterialType.NEWSPAPER.getCode()
                + Newspaper.NewspaperCategory.valueOf(String.valueOf(category)).getCode()
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
