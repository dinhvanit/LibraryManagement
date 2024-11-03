package uet.librarymanagementsystem.entity.documents.materials;

import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.MaterialType;

public class Newspaper extends Document {
    private final NewspaperCategory category;
    private static int newspaperCount = 0;

    public Newspaper(String title, String author, NewspaperCategory category) {
        super(title, author);
        this.category = category;
        this.id = generateId();
    }

    @Override
    protected String generateId() {
        newspaperCount++;
        return MaterialType.newspaper.getCode() + category.getCode() + String.format("%03d", newspaperCount);
    }

    /*
    @Override
    public MaterialType getMaterial() {
        return MaterialType.NEWSPAPER;
    }

    @Override
    public NewspaperCategory getCategory() {
        return category;
    }

     */

    @Override
    public String getMaterial() {
        return "Newspaper";
    }

    @Override
    public String getCategory() {
        return category.name();
    }

    public enum NewspaperCategory {
        //khoi tao cac loai category cua tung material, khoi tao ca id dua vao category cho document o day
        weather("wea"),
        technology("tec"),
        sports("spo");

        private final String code;

        NewspaperCategory(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

}