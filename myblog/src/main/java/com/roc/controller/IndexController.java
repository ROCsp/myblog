package com.roc.controller;


import com.roc.pojo.Blog;
import com.roc.pojo.Comment;
import com.roc.pojo.Tag;
import com.roc.service.*;
import javassist.runtime.Desc;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CompletionService;

@Controller
public class IndexController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private MessageService messageService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 6,sort = "createTime",direction = Sort.Direction.DESC) Pageable pageable, Model model){
        List<Tag> tags = tagService.listTop(6);
        for (Tag tag : tags){
            Long typeBlogsNum = blogService.countBlogsByTagIdAndPublished(tag.getId());
            tag.setPublishedBlogNum(typeBlogsNum);
        }
        model.addAttribute("page",blogService.findAll(pageable));
        model.addAttribute("tags",tags);
        model.addAttribute("types",typeService.listTop(6));
        model.addAttribute("recommendBlogs",blogService.findTop(8));
        return "index";
    }

    @GetMapping("/blog/{id}")
    public String blogs(@PathVariable("id") Long id,Model model){
        model.addAttribute("blog",blogService.findByIdAndConvert(id));
        return "blog";
    }

    @PostMapping("/search")
    public String search(@RequestParam String query, @PageableDefault(sort = "updateTime",direction = Sort.Direction.DESC) Pageable pageable, Model model){
        Page<Blog> blogs = blogService.findAll("%" + query + "%", pageable);
        model.addAttribute("page",blogs);
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/footer/newblog")
    public String footer(Model model){
        Long blogNum = blogService.countBlogsAndPublishedTrue();
        Long commentNum = commentService.countComments();
        Long messageNum = messageService.countMessages();
        model.addAttribute("blogNum",blogNum);
        model.addAttribute("commentNum",commentNum);
        model.addAttribute("messageNum",messageNum);
        return "_fragments :: countData";
    }
}
