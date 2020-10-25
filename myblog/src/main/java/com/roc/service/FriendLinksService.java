package com.roc.service;

import com.roc.pojo.FriendLinks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FriendLinksService {
    FriendLinks getFriendLink(Long id);

    FriendLinks getFriendLink(String addr);

    FriendLinks update(Long id,FriendLinks friendLinks);

    Page<FriendLinks> list(Pageable pageable);

    FriendLinks addFriendLink(FriendLinks friendLinks);

    void delete(Long id);

    List<FriendLinks> listFriendLinks();
}
