package vn.iotstar.service;

import java.util.List;

import vn.iotstar.entity.Video;

public interface IVideoService {

    void insert(Video video);

    void update(Video video);

    void delete(String videoId);

    Video findById(String videoId);

    List<Video> findAll();

    List<Video> searchByTitle(String title);

    List<Video> findByCategory(int categoryId);

    List<Video> findAll(int page, int pagesize);

    int count();
}
