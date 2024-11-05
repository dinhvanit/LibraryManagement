package uet.librarymanagementsystem.entity.documents;

public abstract class Document {
    protected String title;
    protected String author;
    protected String id; // ID sẽ được tạo từ hàm trong DatabaseDO

    public Document(String title, String author) {
        this.title = title;
        this.author = author;
        this.id = ""; // Khởi tạo ID là chuỗi rỗng
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

    public void setId(String id) {
        this.id = id;
    }

    // Phương thức trừu tượng để lấy loại tài liệu
    public abstract String getMaterial();

    // Phương thức trừu tượng để lấy thể loại tài liệu
    public abstract String getCategory();
}
