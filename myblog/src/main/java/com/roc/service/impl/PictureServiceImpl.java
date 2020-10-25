package com.roc.service.impl;

import com.roc.NotFoundException;
import com.roc.dao.PictureDao;
import com.roc.pojo.Picture;
import com.roc.pojo.Type;
import com.roc.service.PictureService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("pictureService")
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureDao pictureDao;

    @Transactional
    @Override
    public Picture save(Picture picture) {
        return pictureDao.save(picture);
    }

    @Transactional
    @Override
    public Picture update(Long id,Picture picture) {
        Picture p = pictureDao.getOne(id);
        if (p == null) {
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(picture,p);
        return pictureDao.save(p);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        pictureDao.deleteById(id);
    }

    @Override
    public Page<Picture> listPictures(Pageable pageable) {
        return pictureDao.findAll(pageable);
    }

    @Override
    public Picture getPictureByLink(String link) {
        return pictureDao.findPictureByLink(link);
    }

    @Override
    public Picture getPictureById(Long id) {
        return pictureDao.findPictureById(id);
    }

    @Override
    public List<Picture> listPictures() {
        return pictureDao.findAll();
    }
}
