package com.example.dusTmq.domain.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
public class MemberDetail implements UserDetails {

    private final Member member;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(() ->{
            return String.valueOf(member.getRole()); //collection에서 role을 넣어줘야 권한이 정상 작동한다.
                                                    // collectors에 넣을때는 반드시 ROLE_xxxx로 넣어줘야한다.
        });
            
//        collectors.stream().forEach(grantedAuthority -> System.out.println("grantedAuthority.getAuthority() = " + grantedAuthority.getAuthority()));
        return collectors;
    }

    @Override
    public String getPassword() {
        return member.getPwd();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
