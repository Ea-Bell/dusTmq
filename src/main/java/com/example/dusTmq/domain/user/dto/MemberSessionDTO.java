package com.example.dusTmq.domain.user.dto;


import com.example.dusTmq.domain.user.Member;
import com.example.dusTmq.domain.user.Role;
import lombok.Getter;


import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class MemberSessionDTO {
    private String email;
    private String pwd;
    private Role role;
    private LocalDateTime lastLoginTime;

    public MemberSessionDTO(Member member) {
        this.email = member.getEmail();
        this.pwd = member.getPwd();
        this.role = member.getRole();
        this.lastLoginTime = member.getLastLoginTime();
    }
}
