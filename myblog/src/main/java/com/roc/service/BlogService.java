package com.roc.service;

import com.roc.pojo.Blog;
import com.roc.pojo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {

    Blog findById(Long id);

    Blog findByIdAndConvert(Long id);

    Page<Blog> listBlogs(Pageable pageable);

    Page<Blog> listBlogs(Pageable pageable, BlogQuery blog);

    List<Blog> listBlogs();

    List<Blog> listBlogsSortByUpdateTime();

    Blog saveBlog(Blog blog);

    Blog update(Long id,Blog blog);

    void delete(Long id);

    List<Blog> findTop(Integer size);

    Page<Blog> findAll(Pageable pageable);

    Page<Blog> findAll(String query,Pageable pageable);

    Page<Blog> findBlogsByTypeId(Long id,Pageable pageable);

    Page<Blog> findBlogsByTagIdAndPublished(Long id, Pageable pageable);

    Long countBlogsByTagIdAndPublished(Long id);

    Long countBlogsAndPublishedTrue();

}
