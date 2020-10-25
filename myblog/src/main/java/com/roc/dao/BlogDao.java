package com.roc.dao;

import com.roc.pojo.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogDao extends JpaRepository<Blog,Long> , JpaSpecificationExecutor<Blog> {

    @Query("select b from  Blog b where b.recommend = true and b.published = true")
    List<Blog> findTop(Pageable pageable);

    @Query("SELECT b from Blog  b where b.published = true")
    Page<Blog> findBlogsPublished(Pageable pageable);

    @Query("SELECT b from Blog  b where b.published = true and b.title like ?1 OR b.content like ?1")
    Page<Blog> findBlogsPublished(String query,Pageable pageable);

    @Modifying
    @Query("update Blog b set b.views = b.views + 1 where b.id = ?1")
    void updateViews(Long blogId);

    @Query("select count(b.id) from Blog b where b.published = true and b.type.id = ?1")
    Integer findTypeBlogsNum(Long typeId);


    Page<Blog> findBlogsByTypeIdAndPublishedTrue(Long id,Pageable pageable);

}
