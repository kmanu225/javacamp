package com.library.controller;

import com.library.model.User;

public class Gateway {

    private User user = new User();

    public Gateway() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
