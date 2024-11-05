package uet.librarymanagementsystem.entity.documents.materials;

import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.MaterialType;

public class Thesis extends Document {
    private final ThesisCategory category;

    public Thesis(String title, String author, ThesisCategory category) {
        super(title, author);
        this.category = category;
    }

    @Override
    public String getMaterial() {
        return MaterialType.THESIS.name(); // Trả về tên enum
    }

    @Override
    public String getCategory() {
        return category.name();
    }

    public enum ThesisCategory {
        ENVIRONMENT,
        COMPUTER_SCIENCE,
        ENGINEERING,

        OTHER; // Thêm các loại category cho luận án
    }
}
