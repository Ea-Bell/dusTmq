package com.example.dusTmq.security;


import com.example.dusTmq.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 페이지에 특정 권한이 있는 유저만 접근을 허용할 경우 권한 ㅁ치 인증을 미리 체크하겠다는 설정을 활성화
@EnableWebSecurity //시큐리티 필터 등록
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberService memberService;
    private final AuthSucessHandler authSucessHandler;
    private final AuthFailureHandler authFailureHandler;
//    private final static String[] PERMIT_ALL_WHITELIST = { "/login/**",};
    private final static String[] USER_WHITELIST = {"/**","/user/**", "/noticeBoard/**"};
    private final static String [] ADMIN_WHITELIST = {"/admin/**"};
    private final static String[] PERMIT_ALL_WHITELIST = {"/css/**", "/js/**", "/img/**","/error","/favicon.ico","/vendor/**","/scss/**","/login/**",};

    @Bean
    public BCryptPasswordEncoder encryptPassword(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(encryptPassword());
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web
//                .ignoring().antMatchers(RESOURCES_WHITELIST);
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .csrf().disable() //csrf공격을 방어 하기위해 csrf기능을 켜두는게 좋다.
                    .authorizeRequests()
                    .antMatchers(PERMIT_ALL_WHITELIST).permitAll()
                    .antMatchers(ADMIN_WHITELIST).access("hasRole('ROLE_ADMIN')")
                    .antMatchers(USER_WHITELIST).access("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
                    .anyRequest()
                    .authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/action")
                    .successHandler(authSucessHandler)
                    .failureHandler(authFailureHandler)
                .permitAll()
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                    .clearAuthentication(true)//권한 정보 제거
                    .permitAll()
                .and()
                    .sessionManagement()
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(false)
                    .expiredUrl("/login?error=true&exception=Have been attempted to login from a new place. or session expired")
                .and()
                .and().rememberMe()
                    .alwaysRemember(false)
                    .tokenValiditySeconds(60)
                    .rememberMeParameter("remember-me");
    }

}
