package com.xuebusi.framework.service;

import com.xuebusi.framework.annotation.Autowired;
import com.xuebusi.framework.annotation.Service;
import com.xuebusi.framework.dao.UserDao;
import com.xuebusi.framework.entity.User;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getUser(String name) {
        return userDao.getUser(name);
    }
}
