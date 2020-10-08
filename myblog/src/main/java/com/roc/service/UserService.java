package com.roc.service;

import com.roc.pojo.User;

/**
 * 根据用户名和密码验证用户
 */
public interface UserService {
    User checkUser(String username,String password);
}
