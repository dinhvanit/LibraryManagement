package uet.librarymanagementsystem.services.userServices;

import uet.librarymanagementsystem.DatabaseOperation.UserDO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

public class AddStudentServiceTest {

    private AddStudentService addStudentService;

    @BeforeEach
    public void setUp() {
        // Khởi tạo AddStudentService trước mỗi test
        addStudentService = new AddStudentService();
    }

    @Test
    public void testAddStudent_Success() throws SQLException {
        // Mock phương thức tĩnh của UserDO để trả về false (sinh viên chưa tồn tại)
        try (MockedStatic<UserDO> mockedStatic = mockStatic(UserDO.class)) {
            mockedStatic.when(() -> UserDO.isUserExists("123")).thenReturn(false); // giả sử sinh viên chưa tồn tại

            // Giả sử hành động thêm sinh viên thành công (mock insertStudent không làm gì)
            doNothing().when(UserDO.class);
            UserDO.insertStudent("123", "Dinh Van", "01/01/2000", "0123456789", "dinhvan@gmail.com", "123");

            // Gọi phương thức addStudent
            String result = addStudentService.addStudent("123", "Dinh Van", "01/01/2000", "0123456789", "dinhvan@gmail.com");

            // Kiểm tra kết quả trả về
            assertEquals("Sinh viên được thêm thành công.", result);

            // Kiểm tra UserDO có gọi phương thức insertStudent không
            mockedStatic.verify(() -> UserDO.insertStudent("123", "Dinh Van", "01/01/2000", "0123456789", "dinhvan@gmail.com", "123"));
        }
    }

    @Test
    public void testAddStudent_AlreadyExists() throws SQLException {
        // Mock phương thức tĩnh của UserDO để trả về true (sinh viên đã tồn tại)
        try (MockedStatic<UserDO> mockedStatic = mockStatic(UserDO.class)) {
            mockedStatic.when(() -> UserDO.isUserExists("123")).thenReturn(true);// sinh viên đã tồn tại

            // Gọi phương thức addStudent
            String result = addStudentService.addStudent("123", "Dinh Van", "01/01/2000", "0123456789", "dinhvan@gmail.com");

            // Kiểm tra kết quả trả về
            assertEquals("Sinh viên với ID 123 đã tồn tại.", result);

            // Không kiểm tra insertStudent vì sinh viên đã tồn tại
            mockedStatic.verify(() -> UserDO.insertStudent(any(), any(), any(), any(), any(), any()), times(0));
        }
    }

    @Test
    public void testAddStudent_SQLException() throws SQLException {
        // Mock phương thức tĩnh của UserDO để trả về một ngoại lệ SQLException khi gọi insertStudent
        try (MockedStatic<UserDO> mockedStatic = mockStatic(UserDO.class)) {
            mockedStatic.when(() -> UserDO.isUserExists("123")).thenReturn(false);

            // Giả sử phương thức insertStudent sẽ ném SQLException
            doThrow(new SQLException("Lỗi khi thêm sinh viên")).when(UserDO.class);
            UserDO.insertStudent("123", "Dinh Van", "01/01/2000", "0123456789", "dinhvan@gmail.com", "123");

            // Gọi phương thức addStudent
            String result = addStudentService.addStudent("123", "Dinh Van", "01/01/2000", "0123456789", "dinhvan@gmail.com");

            // Kiểm tra kết quả trả về
            assertEquals("Lỗi khi thêm sinh viên: Lỗi khi thêm sinh viên", result);

            // Kiểm tra UserDO có gọi phương thức insertStudent và ném SQLException không
            mockedStatic.verify(() -> UserDO.insertStudent("123", "Dinh Van", "01/01/2000", "0123456789", "dinhvan@gmail.com", "123"));
        }
    }
}
