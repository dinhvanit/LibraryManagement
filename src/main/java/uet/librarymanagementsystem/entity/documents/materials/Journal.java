package uet.librarymanagementsystem.entity.documents.materials;

import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.MaterialType;

public class Journal extends Document {
    private final JournalCategory category;
    private static int journalCount = 0;

    public Journal(String title, String author, JournalCategory category) {
        super(title, author);
        this.category = category;
        this.id = generateId();
    }

    @Override
    protected String generateId() {
        journalCount++;
        return MaterialType.journal.getCode() + category.getCode() + String.format("%03d", journalCount);
    }

    /*
    @Override
    public MaterialType getMaterial() {
        return MaterialType.JOURNAL;
    }

    @Override
    public JournalCategory getCategory() {
        return category;
    }

     */

    @Override
    public String getMaterial() {
        return "Journal";
    }

    @Override
    public String getCategory() {
        return category.name();
    }

    public enum JournalCategory {
        //khoi tao cac loai category cua tung material, khoi tao ca id dua vao category cho document o day
        RESEARCH("RS"),
        PROFESSIONAL("PF"),
        GENERAL("GN"),
        STUDENT("ST");

        private final String code;

        JournalCategory(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}
