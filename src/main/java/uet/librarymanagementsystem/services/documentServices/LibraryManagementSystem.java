package uet.librarymanagementsystem.services.documentServices;

public class LibraryManagementSystem {
    public static void main(String[] args) {
        BookLookupService bookLookupService = new BookLookupService("9780261103573");
        if (bookLookupService.checkBookInfoByISBN()) {
            System.out.println(bookLookupService.getIsbn());
            System.out.println(bookLookupService.getTitleBook());
            System.out.println(bookLookupService.getAllAuthors());
            System.out.println(bookLookupService.getTheFirstAuthor());
            System.out.println(bookLookupService.getAllCategories());
            System.out.println(bookLookupService.getTheFirstCategory());
            System.out.println(bookLookupService.getDescription());
            System.out.println(bookLookupService.getLanguage());
            System.out.println(bookLookupService.getPreviewLink());
            System.out.println("thumbnaiURL = " + bookLookupService.getThumbnailUrl());
        } else {
            System.out.println("Ko ton tai");
        }
    }
}
