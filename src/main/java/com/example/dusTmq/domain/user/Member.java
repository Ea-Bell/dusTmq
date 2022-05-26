package com.example.dusTmq.domain.user;

import com.example.dusTmq.domain.board.BoardDetailVO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue
    @Column(name="memberId")
    private Long id;

    @NotEmpty
    private String username;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String pwd;

    @NotNull
    private LocalDateTime regDate;
    @NotNull
    private LocalDateTime updateDate;
    @NotNull
    private LocalDateTime deleteTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Authority authority;

    @Builder
    public Member(String username, String email, String pwd, LocalDateTime regDate, LocalDateTime updateDate, LocalDateTime deleteTime, Authority authority) {
        this.username = username;
        this.email = email;
        this.pwd = pwd;
        this.regDate = regDate;
        this.updateDate = updateDate;
        this.deleteTime = deleteTime;
        this.authority = authority;
    }

    public Member() {

    }
}
