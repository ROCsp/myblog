package com.roc.controller;

import com.roc.dao.TypeDao;
import com.roc.pojo.Blog;
import com.roc.pojo.Type;
import com.roc.service.BlogService;
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
public class TypesController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PathVariable("id") Long id, @PageableDefault(sort = "updateTime",direction = Sort.Direction.DESC) Pageable pageable,Model model){
        List<Type> types = typeService.listType();
        if (id == -1){
            id = types.get(0).getId();
        }
        Page<Blog> blogs = blogService.findBlogsByTypeId(id, pageable);
        model.addAttribute("types",types);
        model.addAttribute("page",blogs);
        model.addAttribute("activeTypeId", id);
        return "types";
    }

}
