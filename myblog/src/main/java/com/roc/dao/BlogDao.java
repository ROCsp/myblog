package com.roc.dao;

import com.roc.pojo.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BlogDao extends JpaRepository<Blog,Long> , JpaSpecificationExecutor<Blog> {
}
