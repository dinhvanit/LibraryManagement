package uet.librarymanagementsystem.entity.documents;

import uet.librarymanagementsystem.entity.documents.materials.*;
import java.util.Map;

public class DocumentFactory {

    public static Document createDocument(String title, String author, String material, String category) {

        /*
        System.out.println("khoi tao cac thanh phan ");
        System.out.println("Creating document with title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Material: " + material);
        System.out.println("Category: " + category);
        *
         */


        material = material.trim().toUpperCase();
        switch (material) {
            case "BOOK":
                return new Book(title, author, Book.BookCategory.valueOf(category));
            case "JOURNAL":
                return new Journal(title, author, Journal.JournalCategory.valueOf(category));
            case "NEWSPAPER":
                return new Newspaper(title, author, Newspaper.NewspaperCategory.valueOf(category));
            case "THESIS":
                return new Thesis(title, author, Thesis.ThesisCategory.valueOf(category));
            // Các loại tài liệu khác cũng tương tự
            default:
                throw new IllegalArgumentException("Unknown material type: " + material);
        }
    }
}

