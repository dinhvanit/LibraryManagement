package uet.librarymanagementsystem.entity.documents.materials;

import javafx.collections.ObservableList;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.MaterialType;
import uet.librarymanagementsystem.services.documentServices.SearchDocumentService;

import java.sql.Date;
import java.sql.SQLException;

public class Book extends Document {
    private final BookCategory category;

    public Book(String title, String author, BookCategory category) {
        super(title, author);
        this.category = category;
    }

    public Book(String id, String title, String author, BookCategory category) {
        super(id, title, author);
        this.category = category;
    }

    public Book(String id, String title, String author, BookCategory category, String dueDate) {
        super(id, title, author, dueDate);
        this.category = category;
    }

    @Override
    public String getMaterial() {
        return MaterialType.BOOK.name(); // Trả về tên enum
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
        this.id = MaterialType.BOOK.getCode()
                + Book.BookCategory.valueOf(String.valueOf(category)).getCode()
                + getTitleCode() + getAuthorCode() + getQuantityCode();
    }

    public void getInfo(Book d) {
        System.out.println(d.getId() + " " + d.getTitle() + " " +  d.getAuthor() + " " +  d.getMaterial()+ " " + d.getCategory());
    }

    public enum BookCategory {
        FICTION("01"),
        MATHS("02"),
        NON_FICTION("03"),
        BIOGRAPHY("04"),
        HISTORY("05"),
        SCIENCE("06"),
        TECHNOLOGY("07"),
        ART("08"),
        MUSIC("09"),
        PHILOSOPHY("10"),
        RELIGION("11"),
        SELF_HELP("12");

        //khoi tao cac loai category cua tung material, khoi tao ca id dua vao category cho document o day

        //OTHERS;

        private final String code;

        BookCategory(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}
