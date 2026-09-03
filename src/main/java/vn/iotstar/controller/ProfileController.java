package vn.iotstar.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import vn.iotstar.entity.User;
import vn.iotstar.service.IUserService;
import vn.iotstar.service.impl.UserServiceImpl;
import vn.iotstar.util.Constants; 

import java.io.File;
import java.io.IOException;

@WebServlet(urlPatterns = {"/profile"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, 
                 maxFileSize = 1024 * 1024 * 5, 
                 maxRequestSize = 1024 * 1024 * 5 * 5)
public class ProfileController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final IUserService userService = new UserServiceImpl();

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return "default.file"; 
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("account") != null) {
            req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User account = (User) session.getAttribute("account");

        try {
            String fullname = req.getParameter("fullname");
            String phone = req.getParameter("phone");

            account.setFullName(fullname);
            account.setPhone(phone);

            // Đồng bộ thư mục lưu với Constants.DIR của ImageController
            String uploadPath = Constants.DIR;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); // Dùng mkdirs để tạo các thư mục cha nếu chưa có
            }

            Part part = req.getPart("images"); 
            if (part != null && part.getSize() > 0) {
                String fileName = getFileName(part); 
                part.write(uploadPath + File.separator + fileName); 
                account.setImages(fileName);
            }

            userService.update(account);

            session.setAttribute("account", account);
            req.setAttribute("message", "Cập nhật Profile thành công!");

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }

        req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
    }
}