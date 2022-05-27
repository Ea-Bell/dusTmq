package com.example.dusTmq.domain.user;

import com.example.dusTmq.domain.board.BoardDetailVO;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Member implements UserDetails {

    @Id
    @GeneratedValue
    @Column(name="member_id")
    private Long id;
    @NotNull
    private String username;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    @NotNull
    private int age;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @NotNull
    private String auth;

    @NotNull
    private LocalDateTime regDate;
    @NotNull
    private LocalDateTime updateDate;
    @NotNull
    private LocalDateTime deleteTime;

//    @NotNull
//    @Enumerated(EnumType.STRING)
//    @Column(name = "user_role")
//    private Authority authority;

    @Builder
    public Member(String username, String email, String password, int age, Gender gender, String auth, LocalDateTime regDate, LocalDateTime updateDate, LocalDateTime deleteTime) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.auth = auth;
        this.regDate = regDate;
        this.updateDate = updateDate;
        this.deleteTime = deleteTime;
    }



    //사용자의 권한을 콜렉션 형태로 반환
    //단, 클래스 자료형은 GrantedAuthority 를 구현해야함
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        for (String role : auth.split(",")) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    //계정 말료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        return true; //만료되어지지 않음
    }

    //계정 잠김 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true;  //잠기지 않음
    }

    //비밀번호 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true; //만료되지 않음
    }
    //계정의 활성화 여부 반환
    @Override
    public boolean isEnabled() {
        return true; //활성화됨
    }
}
