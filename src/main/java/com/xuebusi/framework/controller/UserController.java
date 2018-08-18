package com.xuebusi.framework.controller;

import com.xuebusi.framework.annotation.Autowired;
import com.xuebusi.framework.annotation.Controller;
import com.xuebusi.framework.annotation.RequestMapping;
import com.xuebusi.framework.entity.User;
import com.xuebusi.framework.service.UserService;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getUser")
    public User getUser(String name, String age) {
        User user = userService.getUser(name);
        return user;
    }
}
