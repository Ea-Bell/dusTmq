package com.example.dusTmq.domain.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class MemberRegisterDTO {

    @Email
    private String email;
    private String pwd;
}
