package vn.iotstar.service.impl;

import java.util.List;

import vn.iotstar.dao.IVideoDao;
import vn.iotstar.dao.impl.VideoDao;
import vn.iotstar.entity.Video;
import vn.iotstar.service.IVideoService;

public class VideoServiceImpl implements IVideoService {

    private final IVideoDao videoDao = new VideoDao();

    @Override
    public void insert(Video video) {
        videoDao.insert(video);
    }

    @Override
    public void update(Video video) {
        Video v = this.findById(video.getVideoId());
        if (v != null) {
            videoDao.update(video);
        }
    }

    @Override
    public void delete(String videoId) {
        try {
            videoDao.delete(videoId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Video findById(String videoId) {
        return videoDao.findById(videoId);
    }

    @Override
    public List<Video> findAll() {
        return videoDao.findAll();
    }

    @Override
    public List<Video> searchByTitle(String title) {
        return videoDao.searchByTitle(title);
    }

    @Override
    public List<Video> findByCategory(int categoryId) {
        return videoDao.findByCategory(categoryId);
    }

    @Override
    public List<Video> findAll(int page, int pagesize) {
        return videoDao.findAll(page, pagesize);
    }

    @Override
    public int count() {
        return videoDao.count();
    }
}
