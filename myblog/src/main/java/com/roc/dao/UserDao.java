package com.roc.dao;

import com.roc.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 操作用户表
 */
public interface UserDao extends JpaRepository<User,Long> {
    User findByUserNameAndPassword(String username,String password);
}
