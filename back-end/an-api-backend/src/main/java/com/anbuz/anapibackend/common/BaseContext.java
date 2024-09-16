package com.anbuz.anapibackend.common;

import com.anbuz.anapicommon.model.entity.User;

public class BaseContext {

    public static ThreadLocal<User> threadLocal = new ThreadLocal<>();

    public static void setCurrentUser(User user) {
        threadLocal.set(user);
    }

    public static User getCurrentUser() {
        return threadLocal.get();
    }

    public static void removeCurrentId() {
        threadLocal.remove();
    }

}