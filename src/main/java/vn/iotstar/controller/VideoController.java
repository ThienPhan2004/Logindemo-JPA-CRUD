package vn.iotstar.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Video;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.IVideoService;
import vn.iotstar.service.impl.CategoryServiceImpl;
import vn.iotstar.service.impl.VideoServiceImpl;
import vn.iotstar.util.Constants;

/**
 * CRUD Video hoan chinh bang JPA/Hibernate.
 * Video co quan he ManyToOne voi Category nen form co dropdown chon danh muc.
 */
@MultipartConfig
@WebServlet(urlPatterns = {
        "/admin/videos",
        "/admin/video/add",
        "/admin/video/insert",
        "/admin/video/edit",
        "/admin/video/update",
        "/admin/video/delete"
})
public class VideoController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int PAGE_SIZE = 5;

    private final IVideoService videoService = new VideoServiceImpl();
    private final ICategoryService cateService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getServletPath();

        switch (path) {
            case "/admin/videos": {
                String keyword = req.getParameter("keyword");
                int page = parseIntOrDefault(req.getParameter("page"), 1);
                if (page < 1) page = 1;

                List<Video> list;
                int totalItems;
                if (keyword != null && !keyword.trim().isEmpty()) {
                    list = videoService.searchByTitle(keyword.trim());
                    totalItems = list.size();
                } else {
                    list = videoService.findAll(page - 1, PAGE_SIZE);
                    totalItems = videoService.count();
                }
                int totalPages = (int) Math.ceil((double) totalItems / PAGE_SIZE);

                req.setAttribute("listvideo", list);
                req.setAttribute("keyword", keyword);
                req.setAttribute("currentPage", page);
                req.setAttribute("totalPages", totalPages);
                req.getRequestDispatcher("/WEB-INF/views/admin/video-list.jsp").forward(req, resp);
                break;
            }
            case "/admin/video/add": {
                req.setAttribute("listcate", cateService.findAll());
                req.getRequestDispatcher("/WEB-INF/views/admin/video-add.jsp").forward(req, resp);
                break;
            }
            case "/admin/video/edit": {
                String id = req.getParameter("id");
                Video video = videoService.findById(id);
                req.setAttribute("video", video);
                req.setAttribute("listcate", cateService.findAll());
                req.getRequestDispatcher("/WEB-INF/views/admin/video-edit.jsp").forward(req, resp);
                break;
            }
            case "/admin/video/delete": {
                String id = req.getParameter("id");
                videoService.delete(id);
                resp.sendRedirect(req.getContextPath() + "/admin/videos");
                break;
            }
            default:
                resp.sendRedirect(req.getContextPath() + "/admin/videos");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String path = req.getServletPath();

        if ("/admin/video/insert".equals(path)) {
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            boolean active = "on".equals(req.getParameter("active"));
            int categoryId = Integer.parseInt(req.getParameter("categoryId"));

            Video video = new Video();
            video.setVideoId(UUID.randomUUID().toString());
            video.setTitle(title);
            video.setDescription(description);
            video.setActive(active);
            video.setViews(0);
            video.setCategory(cateService.findById(categoryId));
            video.setPoster(resolvePoster(req, null, null));

            videoService.insert(video);
            resp.sendRedirect(req.getContextPath() + "/admin/videos");
            return;
        }

        if ("/admin/video/update".equals(path)) {
            String videoId = req.getParameter("videoId");
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            boolean active = "on".equals(req.getParameter("active"));
            int categoryId = Integer.parseInt(req.getParameter("categoryId"));
            String posterLink = req.getParameter("poster");

            Video video = videoService.findById(videoId);
            if (video == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/videos");
                return;
            }
            String oldPoster = video.getPoster();
            video.setTitle(title);
            video.setDescription(description);
            video.setActive(active);
            video.setCategory(cateService.findById(categoryId));
            video.setPoster(resolvePoster(req, posterLink, oldPoster));

            videoService.update(video);
            resp.sendRedirect(req.getContextPath() + "/admin/videos");
        }
    }

    private String resolvePoster(HttpServletRequest req, String linkPoster, String oldPoster)
            throws IOException, ServletException {
        File uploadDir = new File(Constants.DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        try {
            Part part = req.getPart("poster1");
            if (part != null && part.getSize() > 0) {
                if (oldPoster != null && !oldPoster.startsWith("https")) {
                    deleteFileQuietly(Constants.DIR + File.separator + oldPoster);
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

        if (linkPoster != null && !linkPoster.trim().isEmpty()) {
            return linkPoster.trim();
        }
        if (oldPoster != null && !oldPoster.trim().isEmpty()) {
            return oldPoster;
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
