package com.roc.service.impl;

import com.roc.dao.UserDao;
import com.roc.pojo.User;
import com.roc.service.UserService;
import com.roc.utils.MD5Util;
import org.aspectj.lang.annotation.AfterReturning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User checkUser(String username, String password) {
        User user = userDao.findByUserNameAndPassword(username, MD5Util.code(password));
        return user;
    }
}
