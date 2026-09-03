package vn.iotstar.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.util.Constants; //

@WebServlet(urlPatterns = "/image")
public class ImageController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        // 1. Lấy tên file ảnh từ tham số URL
        String fname = req.getParameter("fname");
        if (fname == null || fname.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 2. Trỏ tới file ảnh trong thư mục vật lý (Constants.DIR)
        File file = new File(Constants.DIR, fname);
        if (!file.exists()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 3. Xác định loại file (ví dụ: image/jpeg, image/png)
        String contentType = Files.probeContentType(Paths.get(file.getPath()));
        resp.setContentType(contentType != null ? contentType : "application/octet-stream");

        // 4. Đọc file từ ổ cứng và đẩy lên stream cho trình duyệt hiển thị
        try (FileInputStream in = new FileInputStream(file);
             OutputStream out = resp.getOutputStream()) {
            
            byte[] buffer = new byte[4096];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        }
    }
}