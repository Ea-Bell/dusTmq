package com.example.dusTmq.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    ROLE_ADMIN("ADMIN"),
    ROLE_USER("USER");

    private String description;

    Role(String description) {
        this.description = description;
    }
}
