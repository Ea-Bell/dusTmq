package com.example.dusTmq.domain.user.dto;

import com.example.dusTmq.domain.user.Gender;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class MemberDTO {

    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    private String name;
    @NotEmpty
    private String pwd;
    @NotEmpty
    private Gender sex;
    @NotEmpty
    private int age;

}
