package com.roc.service.impl;

import com.roc.NotFoundException;
import com.roc.dao.FriendLinksDao;
import com.roc.pojo.FriendLinks;
import com.roc.service.FriendLinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service("friendLinksService")
public class FriendLinksServiceImpl implements FriendLinksService {

    @Autowired
    private FriendLinksDao friendLinksDao;

    @Override
    public FriendLinks getFriendLink(Long id) {
        return friendLinksDao.getOne(id);
    }

    @Transactional
    @Override
    public FriendLinks update(Long id, FriendLinks friendLinks) {
        FriendLinks one = friendLinksDao.getOne(id);
        if (one == null){
            throw new NotFoundException("该友人链不存在");
        }
        return  friendLinksDao.save(friendLinks);
    }

    @Override
    public Page<FriendLinks> list(Pageable pageable) {
        return friendLinksDao.findAll(pageable);
    }

    @Transactional
    @Override
    public FriendLinks addFriendLink(FriendLinks friendLinks) {
        friendLinks.setCreateTime(new Date());
        return friendLinksDao.save(friendLinks);
    }

    @Override
    public FriendLinks getFriendLink(String addr) {
        return friendLinksDao.findByBlogAddr(addr);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        friendLinksDao.deleteById(id);
    }
}
