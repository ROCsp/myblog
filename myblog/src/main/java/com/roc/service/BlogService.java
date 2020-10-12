package com.roc.service;

import com.roc.pojo.Blog;
import com.roc.pojo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {

    Blog findById(Long id);

    Page<Blog> listBlogs(Pageable pageable);

    Page<Blog> listBlogs(Pageable pageable, BlogQuery blog);

    List<Blog> listBlogs();

    Blog saveBlog(Blog blog);

    Blog update(Long id,Blog blog);

    void delete(Long id);
}
