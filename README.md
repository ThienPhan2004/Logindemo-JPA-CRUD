# Logindemo - Đăng nhập/Đăng ký + CRUD Category/Video bằng JPA (Hibernate)

## Kiến trúc
Controller (Servlet) -> Service -> DAO (JPA/EntityManager) -> MySQL (qua Hibernate)

Toàn bộ tầng Data Access đã chuyển từ JDBC thuần sang JPA/Hibernate.
Không còn `Connection`, `PreparedStatement`, `ResultSet` thủ công — tất cả
thao tác qua `EntityManager` (persist / merge / remove / find / JPQL).

## Cấu trúc package (vn.iotstar)
- `entity`      : User, Category, Video (JPA Entity)
- `config`      : JPAConfig (tạo EntityManager dùng chung)
- `dao` / `dao.impl`         : Interface + cài đặt DAO bằng JPA
- `service` / `service.impl` : Interface + cài đặt Service (business logic)
- `controller`  : Servlet (Login, Register, Logout, Main, Category, Video, Image)
- `filter`      : AdminFilter (chặn `/admin/*`, chỉ cho roleid == 1 vào)
- `util`        : Constants (đường dẫn upload ảnh), PasswordUtil (băm SHA-256)

## Cài đặt & chạy
1. Cài MySQL, chạy `sql/create_database.sql` để tạo database rỗng `logindemo`
   (Hibernate sẽ tự tạo bảng `users`, `categories`, `videos` khi app khởi động
   nhờ `hibernate.hbm2ddl.auto=update`).
2. Mở `src/main/resources/META-INF/persistence.xml`, sửa lại:
   - `jakarta.persistence.jdbc.user`
   - `jakarta.persistence.jdbc.password`
   cho khớp với MySQL trên máy bạn.
3. Import project vào Eclipse/IntelliJ dạng Maven Web Project, deploy lên Tomcat 10+.
4. Đăng ký một tài khoản qua `/register`. Tài khoản mới mặc định `roleid = 5`
   (user thường). Muốn test khu vực quản trị (`/admin/categories`, `/admin/videos`),
   vào MySQL sửa tay: `UPDATE users SET roleid = 1 WHERE username = '...';`

## Luồng chức năng
- `/login`, `/register`, `/logout` : đăng nhập / đăng ký / đăng xuất (JPA).
- `/home` : trang chủ sau đăng nhập, hiện menu quản trị nếu roleid == 1.
- `/admin/categories`, `/admin/category/add|edit|insert|update|delete` : CRUD Category
  (upload ảnh, hoặc nhập link ảnh https, có phân trang + tìm kiếm theo tên).
- `/admin/videos`, `/admin/video/add|edit|insert|update|delete` : CRUD Video
  (chọn Category qua dropdown, upload poster, có phân trang + tìm kiếm theo tiêu đề).
- `/image?fname=...` : đọc ảnh đã upload trong thư mục `Constants.DIR` và trả về trình duyệt.

## Ghi chú
- Ảnh upload được lưu ngoài thư mục webapp (`Constants.DIR`, mặc định là
  `<thư mục home của user>/iotstar_uploads`) để không bị mất khi redeploy WAR.
  Có thể đổi đường dẫn này trong `vn.iotstar.util.Constants`.
- Mật khẩu được băm SHA-256 trước khi lưu (`PasswordUtil`), không lưu plain-text.
- `AdminFilter` bảo vệ toàn bộ `/admin/*`, tự động redirect nếu chưa đăng nhập
  hoặc không phải admin (roleid != 1).
