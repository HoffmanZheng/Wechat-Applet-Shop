package com.github.NervousOrange.wxshop.config;

import com.github.NervousOrange.wxshop.generated.User;

// 只要用户登录过了，把用户信息放在 ThreadLocal 中，供其他服务调用
public class UserContext {

    static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public static User getCurrentUser() {
        return userThreadLocal.get();
    }

    public static void setThreadLocalUser(User user) {
        userThreadLocal.set(user);
    }

}
