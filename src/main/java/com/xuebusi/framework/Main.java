package com.xuebusi.framework;

import com.alibaba.fastjson.JSON;
import com.xuebusi.framework.controller.UserController;
import com.xuebusi.framework.entity.User;
import com.xuebusi.framework.ioc.BeanHelper;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        testIoc();
    }

    private static void testIoc() {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        UserController userController = (UserController) beanMap.get(UserController.class);
        User user = userController.getUser("admin","18");
        System.out.println(JSON.toJSONString(user));
    }
}
