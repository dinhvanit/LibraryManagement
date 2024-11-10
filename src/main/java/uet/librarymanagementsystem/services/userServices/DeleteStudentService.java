package uet.librarymanagementsystem.services.userServices;
import uet.librarymanagementsystem.DatabaseOperation.UserDO;


public class DeleteStudentService {
    public static void deleteStudentByID(String id) {
        UserDO.deleteStudentById(id);
    }
}
