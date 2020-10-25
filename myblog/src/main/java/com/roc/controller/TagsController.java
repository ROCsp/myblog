package com.roc.controller;

import com.roc.pojo.Blog;
import com.roc.pojo.Tag;
import com.roc.pojo.Type;
import com.roc.service.BlogService;
import com.roc.service.TagService;
import com.roc.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagsController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String types(@PathVariable("id") Long id, @PageableDefault(sort = "updateTime",direction = Sort.Direction.DESC) Pageable pageable,Model model){
        List<Tag> tags = tagService.listTag();
        for (Tag tag : tags){
            Long typeBlogsNum = blogService.countBlogsByTagIdAndPublished(tag.getId());
            tag.setPublishedBlogNum(typeBlogsNum);
        }
        if (id == -1){
            id = tags.get(0).getId();
        }
        Page<Blog> blogs = blogService.findBlogsByTagIdAndPublished(id, pageable);
        model.addAttribute("tags",tags);
        model.addAttribute("page",blogs);
        model.addAttribute("activeTagId", id);
        return "tags";
    }

}
