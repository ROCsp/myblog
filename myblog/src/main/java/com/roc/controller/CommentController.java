package com.roc.controller;

import com.roc.pojo.Comment;
import com.roc.pojo.User;
import com.roc.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable("blogId") Long blogId, Model model){
        model.addAttribute("comments",commentService.listCommentsByBlogId(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){

        User user = (User) session.getAttribute("user");
        if (user != null){
            comment.setAvatar(user.getAvatar());
            comment.setEmail(user.getEmail());
            comment.setNickName(user.getNickName());
            comment.setAdmin(true);
        }else {
            comment.setAvatar(avatar);
            comment.setAdmin(false);
        }
        Long blogId = comment.getBlog().getId();
        commentService.save(comment);
        return "redirect:comments/" + blogId;
    }

    @GetMapping("/comments/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        Long blogId = commentService.findBlogIdByCommentId(id);
        commentService.delete(id);
        return "redirect:/blog/" + blogId;
    }
}
