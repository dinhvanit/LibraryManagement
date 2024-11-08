package uet.librarymanagementsystem;

import uet.librarymanagementsystem.entity.documents.materials.Book;
import uet.librarymanagementsystem.services.userServices.AddStudentService;

public class test {
    public static void main(String[] args) {
        // Thử thêm một sinh viên mới
        String id = "23020714";
        String name = "Nguyen Dinh Van";
        String birthday = "03-11-2005";
        String phone = "123456789";
        String email = "nguyendinhvanefto@gmail.com";

        boolean success = AddStudentService.addStudent(id, name, birthday, phone, email);

        if (success) {
            System.out.println("Sinh viên đã được thêm thành công!");
        } else {
            System.out.println("Thêm sinh viên thất bại.");
        }

        /*// Kiểm tra thêm sinh viên với các thông tin thiếu
        String nameInvalid = "";
        boolean successInvalid = AddStudentService.addStudent(id, nameInvalid, birthday, phone, email);

        if (successInvalid) {
            System.out.println("Sinh viên đã được thêm thành công!");
        } else {
            System.out.println("Thêm sinh viên thất bại vì thông tin không đầy đủ.");
        }*/
    }
}
