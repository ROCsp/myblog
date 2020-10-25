package com.roc.controller;

import com.roc.pojo.Blog;
import com.roc.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.awt.print.Pageable;
import java.util.List;

@Controller
public class ArchivesController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model){
        List<Blog> blogs = blogService.listBlogsSortByUpdateTime();
        model.addAttribute("blogs",blogs);
        return "archives";
    }
}
