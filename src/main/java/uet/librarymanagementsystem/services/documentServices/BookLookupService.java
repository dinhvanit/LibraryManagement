package uet.librarymanagementsystem.services.documentServices;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class BookLookupService {


    private static final String API_KEY = "AIzaSyB3XjZWZfnQbDdZ1f4HOtnfoebe0HQ-JD8";

    private static final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes?q=isbn:%s&key=" + API_KEY;

    /**
     * Phương thức để tra cứu thông tin sách qua mã ISBN.
     * @param isbn Mã ISBN của sách.
     * @return JSONObject chứa thông tin của sách (title, authors, publisher, description).
     */
    public static JSONObject fetchBookInfoByISBN(String isbn) {
        String urlString = String.format(GOOGLE_BOOKS_API_URL, isbn);
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
}
