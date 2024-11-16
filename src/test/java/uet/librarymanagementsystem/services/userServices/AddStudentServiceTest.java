package uet.librarymanagementsystem.services.userServices;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.*;
import uet.librarymanagementsystem.DatabaseOperation.UserDO;

import java.sql.SQLException;

public class AddStudentServiceTest {

    @InjectMocks
    private AddStudentService addStudentService; // Lớp cần kiểm thử

    @Test
    public void testAddStudent_Success() {
        // Dữ liệu kiểm thử
        String id = "23020714";
        String name = "Nguyen Dinh Van";
        String dateOfBirth = "03-11-2005";
        String phone = "123456789";
        String email = "nguyendinhvanefto@gmail.com";

        // Mô phỏng hành động kiểm tra xem sinh viên có tồn tại không
        when(UserDO.isUserExists(id)).thenReturn(false); // Mô phỏng là sinh viên chưa tồn tại

        // Mở rộng mock static cho phương thức insertStudent
        try (MockedStatic<UserDO> mockedStatic = mockStatic(UserDO.class)) {
            // Mô phỏng insertStudent
            mockedStatic.when(() -> UserDO.insertStudent(id, name, dateOfBirth, phone, email, id)).thenReturn(null);

            // Gọi phương thức cần kiểm thử
            String result = AddStudentService.addStudent(id, name, dateOfBirth, phone, email);

            // Kiểm tra kết quả trả về
            assertEquals("Sinh viên được thêm thành công.", result);

            // Kiểm tra xem phương thức insertStudent có được gọi không
            mockedStatic.verify(() -> UserDO.insertStudent(id, name, dateOfBirth, phone, email, id));
        }
    }

    @Test
    public void testAddStudent_AlreadyExists() {
        // Dữ liệu kiểm thử
        String id = "23020714";

        // Mô phỏng hành động kiểm tra xem sinh viên có tồn tại không
        when(UserDO.isUserExists(id)).thenReturn(true); // Mô phỏng là sinh viên đã tồn tại

        // Gọi phương thức cần kiểm thử
        String result = AddStudentService.addStudent(id, "Nguyen Dinh Van", "03-11-2005", "123456789", "nguyendinhvanefto@gmail.com");

        // Kiểm tra kết quả trả về
        assertEquals("Sinh viên với ID 23020714 đã tồn tại.", result);
    }

    @Test
    public void testAddStudent_SQLException() throws SQLException {
        // Dữ liệu kiểm thử
        String id = "23020714";

        // Mô phỏng hành động kiểm tra xem sinh viên có tồn tại không
        when(UserDO.isUserExists(id)).thenReturn(false); // Mô phỏng là sinh viên chưa tồn tại

        // Mô phỏng việc gặp lỗi khi thêm sinh viên vào cơ sở dữ liệu
        try (MockedStatic<UserDO> mockedStatic = mockStatic(UserDO.class)) {
            mockedStatic.when(() -> UserDO.insertStudent(id, "Nguyen Dinh Van", "03-11-2005", "123456789", "nguyendinhvanefto@gmail.com", id))
                    .thenThrow(new SQLException("Lỗi cơ sở dữ liệu"));

            // Gọi phương thức cần kiểm thử
            String result = AddStudentService.addStudent(id, "Nguyen Dinh Van", "03-11-2005", "123456789", "nguyendinhvanefto@gmail.com");

            // Kiểm tra kết quả trả về
            assertEquals("Lỗi khi thêm sinh viên: Lỗi cơ sở dữ liệu", result);
        }
    }
}
