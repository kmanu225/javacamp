package com.library.controller;

import com.library.model.Utilisateur;

public class Gateway {

    private Utilisateur user = new Utilisateur();

    public Gateway() {
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }
}
