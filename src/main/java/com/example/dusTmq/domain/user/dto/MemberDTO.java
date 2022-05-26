package com.example.dusTmq.domain.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class MemberDTO {

    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    private String name;
    @NotEmpty
    private String pwd;
    @NotEmpty
    private String sex;
    @NotEmpty
    private int age;

}
