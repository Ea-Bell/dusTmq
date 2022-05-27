package com.example.dusTmq.domain.user.dto;

import com.example.dusTmq.domain.user.Gender;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@ToString
public class MemberLoginDTO {

    @Email
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;


}
