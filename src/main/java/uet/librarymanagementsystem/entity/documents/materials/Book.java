package uet.librarymanagementsystem.entity.documents.materials;

import javafx.collections.ObservableList;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.MaterialType;
import uet.librarymanagementsystem.services.documentServices.SearchDocumentService;

import java.sql.SQLException;

public class Book extends Document {
    private final BookCategory category;
    private String isbn;

    public Book(String title, String author, BookCategory category) {
        super(title, author);
        this.category = category;
    }

    public Book(String id, String title, String author, BookCategory category, String isbn) {
        super(id, title, author);
        this.category = category;
        this.isbn = isbn;
    }

    // Constructor không nhận ISBN (nếu cần thiết)
    public Book(String id, String title, String author, BookCategory category) {
        super(id, title, author);
        this.category = category;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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
            ObservableList<Document> listDocument = searchDocumentService.searchAll(
                    title, author, getMaterial(), getCategory());
            int len = listDocument.size();
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
        this.id = MaterialType.BOOK.getCode()
                + Book.BookCategory.valueOf(String.valueOf(category)).getCode()
                + getTitleCode() + getAuthorCode() + getQuantityCode();
    }

    public void getInfo(Book d) {
        System.out.println(d.getId() + " " + d.getTitle() + " " +  d.getAuthor() + " " +  d.getMaterial()+ " " + d.getCategory());
    }

    public enum BookCategory {
        // Thêm các thể loại chi tiết
        FICTION("01"),                // Tiểu thuyết
        NON_FICTION("02"),            // Phi hư cấu
        SCIENCE("03"),                // Khoa học
        MATHEMATICS("04"),            // Toán học
        TECHNOLOGY("05"),             // Công nghệ
        BIOGRAPHY("06"),              // Tiểu sử
        HISTORY("07"),                // Lịch sử
        RELIGION("08"),               // Tôn giáo
        SELF_HELP("09"),              // Sách tự lực
        HEALTH("10"),                 // Sức khỏe
        EDUCATION("11"),              // Giáo dục
        ART("12"),                    // Nghệ thuật
        MUSIC("13"),                  // Âm nhạc
        PHILOSOPHY("14"),             // Triết học
        SPORTS("15"),                 // Thể thao
        TRAVEL("16"),                 // Du lịch
        COOKING("17"),                // Nấu ăn
        SCIENCE_FICTION("18"),        // Khoa học viễn tưởng
        FANTASY("19"),                // Thần thoại
        OTHERS("99");                 // Các thể loại khác

        private final String code;

        BookCategory(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}
