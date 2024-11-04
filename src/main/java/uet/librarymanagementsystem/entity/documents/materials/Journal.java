package uet.librarymanagementsystem.entity.documents.materials;

import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.MaterialType;

public class Journal extends Document {
    private final JournalCategory category;

    public Journal(String title, String author, JournalCategory category) {
        super(title, author);
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

    public enum JournalCategory {
        RESEARCH,
        REVIEW,
        CASE_STUDY,
        OTHERS; // Thêm các loại category cho tạp chí
    }
}
