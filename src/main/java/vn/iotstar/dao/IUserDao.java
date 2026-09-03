package vn.iotstar.dao;

import vn.iotstar.entity.User;

public interface IUserDao {

    User findByUsername(String username);

    void insert(User user);

    // Thêm hàm update để cập nhật dữ liệu Profile (fullname, phone, images) bằng JPA
    void update(User user);

    boolean checkExistEmail(String email);

    boolean checkExistUsername(String username);
}