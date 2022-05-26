package com.example.dusTmq.domain.user;

public enum Authority {

    ADMINUSER("ADMIN"),
    COMMONUSER("USER");

    private String auth;

    Authority(String auth) {
        this.auth = auth;
    }
}
