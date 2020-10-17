package com.github.NervousOrange.wxshop.config;

import com.github.NervousOrange.wxshop.generated.User;

public class UserContext {

    static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public static User getCurrentUser() {
        return userThreadLocal.get();
    }

    public static void setThreadLocalUser(User user) {
        userThreadLocal.set(user);
    }

}
