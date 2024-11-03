package uet.librarymanagementsystem.entity.documents;

public abstract class Document {
    protected String title;
    protected String author;
    protected String id;

    public Document(String title, String author) {
        this.title = title;
        this.author = author;
    }

    protected abstract String generateId();

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

    // public abstract MaterialType getMaterial();

    // public abstract Enum<?> getCategory();

    // trả về chuỗi
    public abstract String getMaterial();

    public abstract String getCategory();
}
