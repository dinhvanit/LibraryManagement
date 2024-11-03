package uet.librarymanagementsystem.entity.documents.materials;

import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.MaterialType;

public class Book extends Document {
    private final BookCategory category;
    private static int bookCount = 0;

    public Book(String title, String author, BookCategory category) {
        super(title, author);
        this.category = category;
        this.id = generateId();
    }

    @Override
    protected String generateId() {
        bookCount++;
        return MaterialType.book.getCode() + category.getCode() + String.format("%03d", bookCount);
    }

    /*
    @Override
    public MaterialType getMaterial() {
        return MaterialType.BOOK;
    }

    @Override
    public BookCategory getCategory() {
        return category;
    }
    */

    @Override
    public String getMaterial() {
        return "Book";
    }

    @Override
    public String getCategory() {
        return category.name();
    }

    public enum BookCategory {
        //khoi tao cac loai category cua tung material, khoi tao ca id dua vao category cho document o day
        maths("mat"),
        history("his"),
        literature("lit"),
        programming("pro"),
        OOP("oop");


        private final String code;

        BookCategory(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}
