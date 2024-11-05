package uet.librarymanagementsystem.entity.documents.materials;

import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.MaterialType;

public class Book extends Document {
    private final BookCategory category;

    public Book(String title, String author, BookCategory category) {
        super(title, author);
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

    public enum BookCategory {
        FICTION,
        MATHS,
        NON_FICTION,
        BIOGRAPHY,
        HISTORY,
        SCIENCE,
        TECHNOLOGY,
        ART,
        MUSIC,
        PHILOSOPHY,
        RELIGION,
        SELF_HELP,
        //khoi tao cac loai category cua tung material, khoi tao ca id dua vao category cho document o day

        OTHERS;
    }
}
