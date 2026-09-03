package vn.iotstar.service.impl;

import vn.iotstar.dao.IUserDao;
import vn.iotstar.dao.impl.UserDao;
import vn.iotstar.entity.User;
import vn.iotstar.service.IUserService;
import vn.iotstar.util.PasswordUtil;

public class UserServiceImpl implements IUserService {

    private final IUserDao userDao = new UserDao();

    @Override
    public User login(String username, String password) {
        User user = userDao.findByUsername(username);
        if (user != null && PasswordUtil.matches(password, user.getPassWord())) {
            return user;
        }
        return null;
    }

    @Override
    public boolean register(String username, String password, String email, String fullname, String phone) {
        if (userDao.checkExistUsername(username) || userDao.checkExistEmail(email)) {
            return false;
        }
        long millis = System.currentTimeMillis();
        java.sql.Date today = new java.sql.Date(millis);

        String hashedPassword = PasswordUtil.hash(password);
        // Tham số thứ 5 truyền null tương ứng với trường 'images' lúc khởi tạo tài khoản mới
        User newUser = new User(email, username, fullname, hashedPassword, null, 5, phone, today);
        userDao.insert(newUser);
        return true;
    }

    // Thực thi phương thức update gọi xuống UserDao
    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public boolean checkExistEmail(String email) {
        return userDao.checkExistEmail(email);
    }

    @Override
    public boolean checkExistUsername(String username) {
        return userDao.checkExistUsername(username);
    }
}