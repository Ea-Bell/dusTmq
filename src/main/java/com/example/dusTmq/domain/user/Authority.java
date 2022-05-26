package com.example.dusTmq.domain.user;

public enum Authority {

    ADMIN("ADMIN"),
    USER("USER");

    private String auth;

    Authority(String auth) {
        this.auth = auth;
    }
}
