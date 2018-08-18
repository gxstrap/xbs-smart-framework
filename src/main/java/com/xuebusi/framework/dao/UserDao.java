package com.xuebusi.framework.dao;

import com.xuebusi.framework.annotation.Repository;
import com.xuebusi.framework.entity.User;

@Repository
public class UserDao {

    public User getUser(String name) {
        if ("admin".endsWith(name)) {
            User user = new User();
            user.setName("admin");
            user.setAge("18");
            user.setAddr("北京");
            return user;
        }
        return null;
    }
}
