package com.roc.controller.admin;

import com.roc.pojo.*;
import com.roc.service.BlogService;
import com.roc.service.TagService;
import com.roc.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * 博客管理类
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    /**
     * 查询博客列表
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(sort = "id",direction = Sort.Direction.DESC) Pageable pageable, Model model,BlogQuery blog){
        Page<Blog> blogs = blogService.listBlogs(pageable,blog);
        List<Type> types = typeService.listType();
        model.addAttribute("types",types);
        model.addAttribute("page",blogs);
        return "admin/blogs";
    }

    /**
     * 使用get请求时报错
     * org.springframework.web.method.annotation.MethodArgumentTypeMismatchException: Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: "search"
     * Caused by: java.lang.NumberFormatException: For input string: "search"
     * @param pageable
     * @param model
     * @param blog
     * @return
     */
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, Model model, BlogQuery blog){
        Page<Blog> blogs = blogService.listBlogs(pageable,blog);
        model.addAttribute("page",blogs);
        return "admin/blogs :: blogList";
    }

    /**
     * 跳转编辑博客页面
     * @param model
     * @return
     */
    @GetMapping("/blogs/input")
    public String input(Model model){
        List<Tag> tags = tagService.listTag();
        List<Type> types = typeService.listType();
        model.addAttribute("tags",tags);
        model.addAttribute("types",types);
        model.addAttribute("blog",new Blog());
        return "admin/blogs-input";
    }

    @GetMapping("/blogs/input/{id}")
    public String input(@PathVariable("id") Long id,Model model){
        List<Tag> tags = tagService.listTag();
        List<Type> types = typeService.listType();
        model.addAttribute("tags",tags);
        model.addAttribute("types",types);
        Blog b = blogService.findById(id);
        b.init();
        model.addAttribute("blog",b);
        return "admin/blogs-input";
    }

    /**
     * 新增博客
     * @param blog
     * @param session
     * @param attributes
     * @return
     */
    @PostMapping("/blogs")
    public String blogs(Blog blog, HttpSession session, RedirectAttributes attributes){
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setUser((User) session.getAttribute("user"));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog blog1 = blogService.saveBlog(blog);
        if (blog1 == null){
            attributes.addFlashAttribute("message","添加失败");
            return "redirect:/admin/blogs/input";
        }else {
            attributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/admin/blogs";
    }

    /**
     * 修改博客
     * @param id
     * @param blog
     * @param attributes
     * @return
     */
    @PostMapping("/blogs/{id}")
    public String edit(@PathVariable("id") Long id,Blog blog,RedirectAttributes attributes){
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog blog1 = blogService.update(id, blog);
        if (blog1 == null){
            attributes.addFlashAttribute("message","添加失败");
            return "redirect:/admin/blogs/input";
        }else {
            attributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/admin/blogs";
    }

    /**
     * 删除博客
     * @param id
     * @return
     */
    @GetMapping("/blogs/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        blogService.delete(id);
        return "redirect:/admin/blogs";
    }

}
