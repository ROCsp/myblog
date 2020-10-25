package com.roc.controller;

import com.roc.pojo.FriendLinks;
import com.roc.service.FriendLinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FriendsController {

    @Autowired
    private FriendLinksService friendLinksService;

    @GetMapping("/friends")
    public String friends(Model model){
        List<FriendLinks> friends = friendLinksService.listFriendLinks();
        model.addAttribute("friends",friends);
        return "friends";
    }
}
