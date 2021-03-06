package com.example.dusTmq.security;


import com.example.dusTmq.service.member.MemberService;
import com.example.dusTmq.service.oauth.PrincipalOauth2UserService;
import com.example.dusTmq.utill.UtilConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 페이지에 특정 권한이 있는 유저만 접근을 허용할 경우 권한 및 인증을 미리 체크하겠다는 설정을 활성화
@EnableWebSecurity //시큐리티 필터 등록
@RequiredArgsConstructor
@Slf4j
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberService memberService;
    private final PrincipalOauth2UserService principalOauth2UserService;
    private final AuthSucessHandler authSucessHandler;
    private final AuthFailureHandler authFailureHandler;
    private final UtilConfig config;
    private final static String[] USER_WHITELIST = {"/user/**", "/noticeBoard/**","/static"};
    private final static String [] ADMIN_WHITELIST = {"/admin/**", };
    private final static String[] PERMIT_ALL_WHITELIST = {"/css/**", "/js/**", "/img/**","/error/**","/favicon.ico","/vendor/**","/scss/**","/login/**","/register"};
    @Bean
    public BCryptPasswordEncoder encryptPassword(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(encryptPassword());
    }



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
/*  oauth2로그인은 다음번에 하는걸로. 아니면 oauth2인증 서버하나 구축하는게 맘편할듯.
                .oauth2Login()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login")
                .userInfoEndpoint()
                .userService(principalOauth2UserService)
                .and()
                .and()*/
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
                    .rememberMeParameter("remember-me")
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(new AuthenticationEntryPoint() {
//                    @Override
//                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//                        log.error("LocalPort={}, requestURI={}, authException={}, authExceptionStackTrace={}", request.getLocalPort(), request.getRequestURI(), authException.getMessage(), authException.getStackTrace());
//                        response.sendRedirect("/login?Error=AuthError");
//                    }
//                })
//                //xss 공격 시도시 accessDenied 가 되어짐.
//                .accessDeniedHandler(new AccessDeniedHandler() {
//                    @Override
//                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//                        log.error("requestURI={}, accessDeniedException={}, accessDeniedExceptionStackTrace={}",request.getRequestURI(), accessDeniedException.getMessage(), accessDeniedException.getStackTrace());
//                        throw accessDeniedException;
////                        response.setStatus(403);
////                        response.sendRedirect(request.getRequestURI()+"?Error=AccessDenied");
//                    }
//                })
                ;


        http
                .headers()
                .xssProtection()
                .and()
                .contentSecurityPolicy("script-src 'self' 'nonce-"+config.getNonce()+"';") //script에 대해 nonce의 값이 없으면 보안 정책에러
        ;
    }

}
