package com.roc.service;

import com.roc.pojo.Picture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PictureService {

    Picture save(Picture picture);

    Picture update(Long id ,Picture picture);

    void delete(Long id);

    Page<Picture> listPictures(Pageable pageable);

    Picture getPictureByLink(String link);

    Picture getPictureById(Long id);

    List<Picture> listPictures();
}
