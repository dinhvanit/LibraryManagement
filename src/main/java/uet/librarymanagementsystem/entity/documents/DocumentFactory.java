package uet.librarymanagementsystem.entity.documents;

import uet.librarymanagementsystem.entity.documents.materials.*;

public class DocumentFactory {

    public static Document createDocument(String id, String title, String author, String material, String category, String isbn) {

        material = material.trim().toUpperCase();
        switch (material) {
            case "BOOK":
                return new Book(id, title, author, Book.BookCategory.valueOf(category), isbn);
            case "JOURNAL":
                return new Journal(id, title, author, Journal.JournalCategory.valueOf(category));
            case "NEWSPAPER":
                return new Newspaper(id, title, author, Newspaper.NewspaperCategory.valueOf(category));
            case "THESIS":
                return new Thesis(id, title, author, Thesis.ThesisCategory.valueOf(category));
            // Các loại tài liệu khác cũng tương tự
            default:
                throw new IllegalArgumentException("Unknown material type: " + material);
        }
    }
}

