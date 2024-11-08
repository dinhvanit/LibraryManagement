package uet.librarymanagementsystem.services.userServices;
import uet.librarymanagementsystem.DatabaseOperation.UserDO;

public class DeleteStudentService {
    public static boolean deleteStudentByID(String ID) {
        if (UserDO.isUserExists(ID)) {
            System.out.println("Sinh viên với ID " + ID + " đã tồn tại.");
            return false;
        }
        return false;
    }
}
