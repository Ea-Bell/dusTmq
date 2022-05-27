package com.example.dusTmq.security;

import com.example.dusTmq.common.AuthFailureHandler;
import com.example.dusTmq.common.AuthSucessHandler;
import com.example.dusTmq.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberService memberService;
    private final AuthFailureHandler authFailureHandler;
    private final AuthSucessHandler authSucessHandler;
    private static final String[] RESOURCES_WHITELIST = {
            "/css/**"
            ,"/js/**"
            ,"/img/**"
            ,"/scss/**"
            ,"/vendor/**"
    };
    private static final String[] PERMIT_ALL_WHITELIST = {
            "/login"
            ,"/login/**"
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

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers(AUTH_WHITELIST);
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers(PERMIT_ALL_WHITELIST).permitAll()
                    .antMatchers(RESOURCES_WHITELIST).permitAll()
                    .antMatchers(USER_PERMIT_WHITELIST).hasRole("ROLL_USER") //유저권한으로 접속 가능한 페이지
                    .antMatchers(ADMIN_PERMIT_WHITELIST).hasRole("ROLL_ADMIN") // 관리자 권한으로 접속 가능한 페이지
                    .anyRequest() //다른요청들에 대해서는
                    .authenticated() // 권한의 종류에 상관 없이 권한이 있어야 접근가능
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/login_proc") //로그인의 Form  Action Url과 맞춰야 한다.
                    .usernameParameter("email")  //디폴트가 username
                    .passwordParameter("pwd")    //디폴트가 password 라서 다른걸로 바꿔쓸때는 반드시 바꿔줘야한다.
                    .successHandler(authSucessHandler)
                    .failureHandler(authFailureHandler)
                    .defaultSuccessUrl("/")
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //로그아웃 URL
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)  //세션 날리기
                .deleteCookies("JSESSIONID", "remember-me") // JSESSIONID, remember-me 쿠키 삭제

                //중복 로그인 체크
                .and()
                    .sessionManagement()
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(false) //false 이면 중복 로그인하면 이전 로그인이 풀린다.
                    .expiredUrl("/login?error=true&exception= Have been attempted to login from a nwe place. or session expired") // 세션이 만료된 경우 이동 할 페이지를 지정
                .and()
                .and()
                    .rememberMe() //로그인 유지
                    .alwaysRemember(false)
                    .tokenValiditySeconds(43200)  // in seconds, 12시간 유지
                    .rememberMeParameter("remember-me");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(memberService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
