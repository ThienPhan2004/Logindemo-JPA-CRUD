package vn.iotstar.util;

import java.io.File;

/**
 * Cac hang so dung chung cho toan bo ung dung.
 */
public class Constants {

    /**
     * Thu muc luu file anh upload (Category, Video...).
     * MAC DINH: thu muc "uploads" trong home directory cua user chay Tomcat,
     * de dam bao chay duoc tren moi he dieu hanh (Windows/Linux/Mac) ma khong
     * can quyen ghi vao thu muc cai dat Tomcat.
     * Ban co the doi sang duong dan co dinh khac neu muon, vi du:
     *   "C:/uploads"  (Windows)
     *   "/var/uploads" (Linux)
     */
    public static final String DIR = System.getProperty("user.home") + File.separator + "iotstar_uploads";

    public static final String DEFAULT_AVATAR = "avatar.png";
}
