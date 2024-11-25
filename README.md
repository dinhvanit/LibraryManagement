# Library Management System

Project for OOP UET: Library Management System
<hr>


## Team Members
- Dương Thái Trân - 23020712
- Nguyễn Đình Văn - 23020714
- Đặng Đình Khang - 23020675
- Nguyễn Thành Minh - 23020684

# Giới thiệu
- Ứng dụng quản lí thư viện dùng để quản lí tài liệu và người dùng trong thư viện.
- Được viết bằng ngôn ngữ Java và hỗ trợ bởi thư viện JavaFX và Sence Builder để tạo ra giao diện đồ hoạ thú vị, đẹp mắt.

## Table of contents
- [Chức năng](#Chức-năng)
  - [Admin](#Admin)
    - [Quản lý tài liệu](#Quản-lý-tài-liệu)
    - [Quản lý sinh viên](#Quản-lý-sinh-viên)
    - [Quản lý giao dịch của sinh viên](#Quản-lý-giao-dịch-của-sinh-viên)
  - [Student](#Student)
    - [Mượn, trả tài liệu](#Mượn-trả-tài-liệu)
    - [Quản lý giao dịch của mình](#Quản-lý-giao-dịch-của-mình)
- [Công nghệ sử dụng](#Công-nghệ-sử-dụng)
  - [JavaFX, Scene Builer](#JavaFX-Scene-Builer)
  - [Google Books API](#Google-Books-API)
  - [Goole Gmail API](#Google-Gmail-API)
  - [PDF](#PDF)
  - [SQLite](#SQLite)

# Chức năng
Các tính năng mà ứng dụng sẽ hỗ trợ.
## Admin
### Quản lý tài liệu
- Tìm kiếm, thêm, xóa, xem thông tin tài liệu tài liệu.
- Nhập liệu tự động bằng isbn đối với book.
### Quản lý sinh viên
- Tìm kiếm, thêm, xóa, sửa (mật khẩu), xem thông tin người dùng.
### Quản lý giao dịch của sinh viên
- Tìm kiếm, xem thông tin giao dịch, xuất PDF, xuất QR code.
- Gửi gmail đến những sinh viên đã quá hạn trả sách.

## Student
### Mượn, trả tài liệu
- Gợi ý tài liệu.
- Tìm kiếm, xem thông tin, mượn, trả tài liệu.
### Quản lý giao dịch của mình
- Xem, xuất QR code, xuất PDF của giao dịch.
- Đánh giá tài liệu.

# Công nghệ sử dụng
Các kĩ thuật, công nghệ sử dụng để xây dựng ứng dụng.

## JavaFX, Scene Builder
- Sử dụng JavaFX và Scene Builder để tạo giao diện đồ hoạ cho ứng dụng.
## Google Books API
- Sử dụng Google Books API để lấy thông tin và điền tự động.
## Google Gmail API
- Sử dụng Google Gmail API để gửi mail cho những sinh viên đã đến hạn nhưng chưa trả sách.
## PDF 
- Xuất file PDF.
## SQLite
- Truy vấn và quản lí tài nguyên.

