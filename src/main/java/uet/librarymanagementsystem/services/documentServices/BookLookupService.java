package uet.librarymanagementsystem.services.documentServices;

import org.json.JSONArray;
import org.json.JSONObject;
import uet.librarymanagementsystem.LMSApplication;
import uet.librarymanagementsystem.services.TaskService;
import uet.librarymanagementsystem.entity.documents.materials.Book;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

public class BookLookupService {
    private static final String API_KEY = "AIzaSyAQoo72W_7jjfm95oTukuLCa4Y8SqRrNGc";
    // key du phong
    // key1: AIzaSyB3XjZWZfnQbDdZ1f4HOtnfoebe0HQ-JD8
    // key2: AIzaSyAIjS9fiavDgdjrYG9ePatAs8tOOE3VK5Y
    // key3: AIzaSyC4zneS0O_o98El3X90MkDfmL_x0uvKm9U
    private static final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes?q=isbn:";

    // Cache để lưu trữ thông tin sách đã tìm kiếm
    private static final Map<String, JSONObject> isbnCache = new HashMap<>();

    private final String isbn;
    private final JSONObject bookInfo;

    public BookLookupService(String isbn) {
        this.isbn = isbn;
        this.bookInfo = fetchBookInfoByISBN();
    }

    public JSONObject getBookInfo() {
        return bookInfo;
    }

    private JSONObject fetchBookInfoByISBN() {
        // Kiểm tra xem thông tin sách đã có trong cache chưa
        if (isbnCache.containsKey(isbn)) {
            System.out.println("Fetching from cache for ISBN: " + isbn);
            return isbnCache.get(isbn); // Trả về thông tin từ cache nếu đã có
        }

        // Nếu chưa có trong cache, lấy thông tin từ API
        TaskService taskService = LMSApplication.getTaskService();

        // Chạy tác vụ tìm kiếm book info trên thread pool của TaskService
        Future<JSONObject> future = taskService.runTask(() -> {
            String urlString = GOOGLE_BOOKS_API_URL + isbn + "&key=" + API_KEY;
            System.out.println("Fetching: " + urlString);
            try {
                // Kết nối đến API
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                // Đọc phản hồi từ API
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder content = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                // Đóng kết nối
                in.close();
                conn.disconnect();

                // Xử lý JSON
                JSONObject jsonResponse = new JSONObject(content.toString());
                JSONArray items = jsonResponse.optJSONArray("items");

                if (items != null && items.length() > 0) {
                    JSONObject bookDetails = items.getJSONObject(0).getJSONObject("volumeInfo");
                    // Lưu vào cache
                    isbnCache.put(isbn, bookDetails);
                    return bookDetails;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });

        try {
            return future.get(5, TimeUnit.SECONDS); // Chờ tối đa 5 giây để lấy kết quả
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            System.err.println("Error fetching book info: " + e.getMessage());
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
