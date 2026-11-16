# Online Department Store - Đề 37

Cài đặt hai module của đề 37 (hệ thống quản lý cửa hàng bách hóa trực tuyến) theo bản thiết kế:

- Khách hàng theo dõi hóa đơn (có nhánh hủy đơn khi hóa đơn còn Chờ xử lý).
- Nhân viên quản lý xem thống kê nhà cung cấp theo doanh thu.

Sinh viên: Nguyễn Quý Tuấn - B23DCVT440.

## Công nghệ

- Java (JDK 8 trở lên), giao diện Java Swing.
- MySQL, kết nối qua JDBC (thư viện `mysql-connector-j` khai báo trong `pom.xml`).

## Cấu trúc project

```
OnlineDepartmentStore
├── pom.xml
├── database/department_store.sql   script tạo 11 bảng và dữ liệu mẫu
├── images/                         ảnh minh họa hàng hóa
└── src/main/java
    ├── model                       16 lớp thực thể
    ├── dao                         DAO0 và các lớp DAO
    └── view
        ├── member                  LoginFrm0
        ├── customer                CustomerHomeFrm0, InvoiceTrackingFrm0, InvoiceDetailFrm0
        └── management              ManagementStaffFrm0, StatisticsSelectionFrm0,
                                    SupplierStatisticsFrm0, SupplierReceiptFrm0, ImportReceiptDetailFrm0
```

## Cách chạy

1. Chạy file `database/department_store.sql` trong MySQL để tạo CSDL `department_store`.
2. Sửa `dbUrl`, `username`, `password` trong `src/main/java/dao/DAO0.java` cho đúng cấu hình MySQL trên máy.
3. Mở thư mục project bằng IDE (IntelliJ IDEA, NetBeans, Eclipse hoặc VS Code) dưới dạng project Maven.
4. Chạy lớp `view.member.LoginFrm0` với thư mục làm việc là thư mục gốc của project.

## Tài khoản mẫu

| Vai trò | Tên đăng nhập | Mật khẩu |
|---|---|---|
| Khách hàng | khachhang | 123456 |
| Nhân viên quản lý | quanly | 123456 |
