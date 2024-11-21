package uet.librarymanagementsystem.services.documentServices;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import uet.librarymanagementsystem.entity.documents.materials.Book;

public class BookLookupService {
    private static final String API_KEY = "AIzaSyB3XjZWZfnQbDdZ1f4HOtnfoebe0HQ-JD8";

    private static final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes?q=isbn:";

    private final String isbn;
    private final JSONObject bookInfo;

    public BookLookupService(String isbn) {
        this.isbn = isbn;
        this.bookInfo = fetchBookInfoByISBN();
    }

    public JSONObject fetchBookInfoByISBN() {
        String urlString = GOOGLE_BOOKS_API_URL + isbn + "&key" + API_KEY;
        System.out.println(urlString);
        try {
            // Tạo URL và mở kết nối
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Đọc phản hồi từ API
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Đóng kết nối
            in.close();
            conn.disconnect();

            // Parse JSON từ phản hồi
            JSONObject jsonResponse = new JSONObject(content.toString());
            JSONArray items = jsonResponse.optJSONArray("items");

            // Nếu có sách, trả về thông tin của cuốn sách đầu tiên
            if (items != null && items.length() > 0) {
                return items.getJSONObject(0).getJSONObject("volumeInfo");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkBookInfoByISBN() {
        return bookInfo != null;
    }

    public String getTitleBook() {
        return bookInfo.optString("title", "N/A");
    }

    public String getAllAuthors() {
        String allAuthors = "N/A";
        JSONArray authorsArray = bookInfo.optJSONArray("authors");

        if (authorsArray != null && !authorsArray.isEmpty()) {
            allAuthors = authorsArray.toList().stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));

        }
        return allAuthors;
    }

    public String getTheFirstAuthor() {
        String firstAuthor = "N/A";
        JSONArray authorsArray = bookInfo.optJSONArray("authors");

        if (authorsArray != null && !authorsArray.isEmpty()) {
            firstAuthor = bookInfo.getJSONArray("authors").getString(0);
        }
        return firstAuthor;
    }

    public String getAllCategories() {
        String allCategories = "N/A";
        JSONArray categoriesArray = bookInfo.optJSONArray("categories");

        if (categoriesArray != null && !categoriesArray.isEmpty()) {
            allCategories = categoriesArray.toList().stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
        }
        return allCategories;
    }

    public String getTheFirstCategory() {
        JSONArray categoriesArray = bookInfo.optJSONArray("categories");
        if (categoriesArray != null && !categoriesArray.isEmpty()) {
            String firstCategory = categoriesArray.optString(0, "N/A").toUpperCase();
            try {
                // nếu có trong enum rồi thì trả về category đấy
                Book.BookCategory.valueOf(firstCategory);
                return firstCategory;
            } catch (IllegalArgumentException e) {
                // còn nếu không thì đặt là others hết
                return Book.BookCategory.OTHERS.name();
            }
        }
        return Book.BookCategory.OTHERS.name(); // Mặc định nếu không có thể loại
    }

    public String getThumbnailUrl() {
        String thumbnailUrl = "N/A";
        JSONObject imageLinks = bookInfo.optJSONObject("imageLinks");

        if (imageLinks != null) {
            thumbnailUrl = imageLinks.optString("thumbnail", "N/A");
        } else {
            System.out.println("No imageLinks found.");
        }
        return thumbnailUrl;
    }

    public String getDescription() {
        return bookInfo.optString("description", "N/A");
    }

    public String getPublisher() {
        return bookInfo.optString("publisher", "N/A");
    }

    public String getLanguage() {
        return bookInfo.optString("language", "N/A");
    }

    public String getPreviewLink() {
        return bookInfo.optString("previewLink", "N/A");
    }

    public String getIsbn() {
        return isbn;
    }
}
