package com.roc.dao;

import com.roc.pojo.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PictureDao extends JpaRepository<Picture,Long> , JpaSpecificationExecutor<Picture> {
    Picture findPictureByLink(String link);

    Picture findPictureById(Long id);
}
