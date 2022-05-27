package com.example.dusTmq.domain.user;

public enum Authority {

    ROLL_ADMIN("ROLL_ADMIN"),
    ROLL_USER("ROLL_USER");

    private String auth;

    Authority(String auth) {
        this.auth = auth;
    }
}
