package vn.iotstar.service;

import vn.iotstar.entity.User;

public interface IUserService {

    User login(String username, String password);

    boolean register(String username, String password, String email, String fullname, String phone);

    boolean checkExistEmail(String email);

    boolean checkExistUsername(String username);
}
