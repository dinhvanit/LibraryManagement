package uet.librarymanagementsystem.entity.documents.materials;

import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.MaterialType;

public class Newspaper extends Document {
    private final NewspaperCategory category;

    public Newspaper(String title, String author, NewspaperCategory category) {
        super(title, author);
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

    public enum NewspaperCategory {
        WEATHER,
        TECHNOLOGY,
        SPORTS,
        ENTERTAINMENT,
        OTHER; // Thêm các loại category cho báo
    }
}
