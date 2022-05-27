package com.example.dusTmq.security;

import com.example.dusTmq.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberService memberService;
    private static final String[] AUTH_WHITELIST = {
            "/css/**"
            ,"/js/**"
            ,"/img/**"
            ,"/scss/**"
            ,"/vendor/**"
    };
    private static final String[] PERMIT_ALL_WHITELIST = {
            "/login/**"
            , "/signup"
            , "/user"
            , "/"
            , "/index"
            , "/error/404"
            , "/noticeBoard"
    };

    private static final String[] USER_PERMIT_WHITELIST = {
            "/noticeBoard/**"
    };

    private static final String[] ADMIN_PERMIT_WHITELIST = {
            "/admin/**"
    };

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(AUTH_WHITELIST);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(PERMIT_ALL_WHITELIST).permitAll()
                .antMatchers(USER_PERMIT_WHITELIST).hasRole("ROLL_USER") //유저권한으로 접속 가능한 페이지
                .antMatchers(ADMIN_PERMIT_WHITELIST).hasRole("ROLL_ADMIN") // 관리자 권한으로 접속 가능한 페이지
                .anyRequest() //다른요청들에 대해서는
                .authenticated() // 권한의 종류에 상관 없이 권한이 있어야 접근가능
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/")
                .and()
                    .logout()
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true);  //세션 날리기

        //중복 로그인 체크
        http.sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false); //false 이면 중복 로그인하면 이전 로그인이 풀린다.
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(memberService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
