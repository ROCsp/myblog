package com.roc.dao;

import com.roc.pojo.FriendLinks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureDao extends JpaRepository<FriendLinks,Long> {
    FriendLinks findByBlogAddr(String addr);
}
