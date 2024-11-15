package uet.librarymanagementsystem.services.documentServices;

import org.json.JSONObject;

public class LibraryManagementSystem {

    /**
     * Phương thức để hiển thị thông tin sách dựa trên ISBN.
     * @param isbn Mã ISBN của sách.
     */
    public void displayBookInfoByISBN(String isbn) {
        JSONObject bookInfo = BookLookupService.fetchBookInfoByISBN(isbn);

        if (bookInfo != null) {

            String title = bookInfo.optString("title", "N/A");
            String authors = bookInfo.optJSONArray("authors") != null ? bookInfo.getJSONArray("authors").join(", ") : "N/A";
            String publisher = bookInfo.optString("publisher", "N/A");
            String description = bookInfo.optString("description", "N/A");


            System.out.println("Title: " + title);
            System.out.println("Authors: " + authors);
            System.out.println("Publisher: " + publisher);
            System.out.println("Description: " + description);
        } else {
            System.out.println("No information found for ISBN: " + isbn);
        }
    }

    public static void main(String[] args) {
        LibraryManagementSystem lms = new LibraryManagementSystem();
        lms.displayBookInfoByISBN("1551640260");
    }
}
