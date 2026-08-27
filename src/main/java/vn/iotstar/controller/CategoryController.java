package vn.iotstar.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.iotstar.entity.Category;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.impl.CategoryServiceImpl;
import vn.iotstar.util.Constants;

/**
 * CRUD Category hoan chinh bang JPA/Hibernate (thay cho JDBC).
 * Ho tro: danh sach + phan trang + tim kiem, them, sua, xoa, upload anh.
 */
@MultipartConfig
@WebServlet(urlPatterns = {
        "/admin/categories",
        "/admin/category/add",
        "/admin/category/insert",
        "/admin/category/edit",
        "/admin/category/update",
        "/admin/category/delete"
})
public class CategoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int PAGE_SIZE = 5;

    private final ICategoryService cateService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getServletPath();

        switch (path) {
            case "/admin/categories": {
                String keyword = req.getParameter("keyword");
                int page = parseIntOrDefault(req.getParameter("page"), 1);
                if (page < 1) page = 1;

                List<Category> list;
                int totalItems;
                if (keyword != null && !keyword.trim().isEmpty()) {
                    list = cateService.searchByName(keyword.trim());
                    totalItems = list.size();
                } else {
                    list = cateService.findAll(page - 1, PAGE_SIZE);
                    totalItems = cateService.count();
                }
                int totalPages = (int) Math.ceil((double) totalItems / PAGE_SIZE);

                req.setAttribute("listcate", list);
                req.setAttribute("keyword", keyword);
                req.setAttribute("currentPage", page);
                req.setAttribute("totalPages", totalPages);
                req.getRequestDispatcher("/WEB-INF/views/admin/category-list.jsp").forward(req, resp);
                break;
            }
            case "/admin/category/add":
                req.getRequestDispatcher("/WEB-INF/views/admin/category-add.jsp").forward(req, resp);
                break;
            case "/admin/category/edit": {
                int id = Integer.parseInt(req.getParameter("id"));
                Category category = cateService.findById(id);
                req.setAttribute("cate", category);
                req.getRequestDispatcher("/WEB-INF/views/admin/category-edit.jsp").forward(req, resp);
                break;
            }
            case "/admin/category/delete": {
                int id = Integer.parseInt(req.getParameter("id"));
                cateService.delete(id);
                resp.sendRedirect(req.getContextPath() + "/admin/categories");
                break;
            }
            default:
                resp.sendRedirect(req.getContextPath() + "/admin/categories");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String path = req.getServletPath();

        if ("/admin/category/insert".equals(path)) {
            String categoryname = req.getParameter("categoryname");
            int status = parseIntOrDefault(req.getParameter("status"), 1);
            String images = req.getParameter("images");

            Category category = new Category();
            category.setCategoryname(categoryname);
            category.setStatus(status);
            category.setImages(resolveImage(req, images, null));

            cateService.insert(category);
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
            return;
        }

        if ("/admin/category/update".equals(path)) {
            int categoryId = Integer.parseInt(req.getParameter("categoryId"));
            String categoryname = req.getParameter("categoryname");
            int status = parseIntOrDefault(req.getParameter("status"), 1);
            String images = req.getParameter("images");

            Category category = cateService.findById(categoryId);
            if (category == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/categories");
                return;
            }
            String oldImage = category.getImages();
            category.setCategoryname(categoryname);
            category.setStatus(status);
            category.setImages(resolveImage(req, images, oldImage));

            cateService.update(category);
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
        }
    }

    /**
     * Xu ly logic chon anh: uu tien file upload > link nhap tay > anh cu (khi update) > anh mac dinh.
     */
    private String resolveImage(HttpServletRequest req, String linkImage, String oldImage)
            throws IOException, ServletException {
        File uploadDir = new File(Constants.DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        try {
            Part part = req.getPart("images1");
            if (part != null && part.getSize() > 0) {
                // Xoa anh cu tren dia neu co va khong phai la link https
                if (oldImage != null && !oldImage.startsWith("https")) {
                    deleteFileQuietly(Constants.DIR + File.separator + oldImage);
                }
                String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                int idx = filename.lastIndexOf(".");
                String ext = (idx >= 0) ? filename.substring(idx + 1) : "png";
                String fname = System.currentTimeMillis() + "." + ext;
                part.write(Constants.DIR + File.separator + fname);
                return fname;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (linkImage != null && !linkImage.trim().isEmpty()) {
            return linkImage.trim();
        }
        if (oldImage != null && !oldImage.trim().isEmpty()) {
            return oldImage;
        }
        return Constants.DEFAULT_AVATAR;
    }

    private void deleteFileQuietly(String filePath) {
        try {
            Path path = Paths.get(filePath);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int parseIntOrDefault(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
