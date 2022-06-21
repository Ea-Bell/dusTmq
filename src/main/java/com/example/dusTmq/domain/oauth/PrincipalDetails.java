package com.example.dusTmq.domain.oauth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user;
    private Map<String, Object> attributes;

    //UserDetails: Form 로그인 시 사용
    public PrincipalDetails(User user){
        this.user=user;
    }
    public PrincipalDetails(User user, Map<String, Object> attributes){
        this.user=user;
        this.attributes=attributes;
    }


    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(() ->{
            return String.valueOf(user.getRole()); //collection에서 role을 넣어줘야 권한이 정상 작동한다.
        });

        return collectors;
    }

    /**
     * UserDetails 구현
     * 비밀번호를 리턴
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }
    /**
     * UserDetails 구현
     * PK값을 반환한다.
     * @return
     */
    @Override
    public String getUsername() {
        return getUsername();
    }

    /**
     * UserDetails 구현
     * 계정 만료 여부
     * true: 만료안됨
     * false: 만료됨.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * UserDetails 구현
     * 계정 잠김 여부
     * true: 잠기지 않음 
     * false: 잠김
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * UserDetails 구현
     * 계정 비밀번호 만료 여부
     * true: 만료안됨
     * false: 만료됨
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * UserDetails 구현
     * 계정 활성화 여부
     * true: 활성화됨
     * false: 비 활성화됨
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * OAuth2User 구현
     */
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * OAuth2User구현
     * @return
     */

    @Override
    public String getName() {
        String sub = attributes.get("sub").toString();
        return sub;
    }
}
