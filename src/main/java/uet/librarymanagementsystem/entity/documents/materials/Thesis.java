package uet.librarymanagementsystem.entity.documents.materials;

import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.MaterialType;

public class Thesis extends Document {
    private final ThesisCategory category;
    private static int thesisCount = 0;

    public Thesis(String title, String author, ThesisCategory category) {
        super(title, author);
        this.category = category;
        this.id = generateId();
    }

    @Override
    protected String generateId() {
        thesisCount++;
        return MaterialType.thesis.getCode() + category.getCode() + String.format("%03d", thesisCount);
    }

    @Override
    public String getMaterial() {
        return "Thesis";
    }

    @Override
    public String getCategory() {
        return category.name();
    }

    public enum ThesisCategory {
        //khoi tao cac loai category cua tung material, khoi tao ca id dua vao category cho document o day
        environment("evi"),
        computerscience("com");

        private final String code;

        ThesisCategory(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}

