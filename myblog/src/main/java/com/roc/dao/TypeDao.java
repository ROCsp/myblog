package com.roc.dao;

import com.roc.pojo.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;


public interface TypeDao extends JpaRepository<Type,Long> {
    Type findByTypeName(String name);

    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
