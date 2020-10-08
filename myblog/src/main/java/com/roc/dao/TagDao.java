package com.roc.dao;

import com.roc.pojo.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;


public interface TagDao extends JpaRepository<Tag,Long> {
    Tag findByTagName(String name);

    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
