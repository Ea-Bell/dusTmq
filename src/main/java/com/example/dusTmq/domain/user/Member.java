package com.example.dusTmq.domain.user;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Member {

    @Id
    @GeneratedValue
    @Column(name="member_id")
    private Long id;
    @NotNull
    @Email
    @Column(unique = true)
    private String email;
    @NotNull
    private String pwd;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;
    @Builder
    public Member(Long id, String email, String pwd, LocalDateTime lastLoginTime, Role role) {
        this.id = id;
        this.email = email;
        this.pwd = pwd;
        this.lastLoginTime = lastLoginTime;
        this.role = role;
    }
}
