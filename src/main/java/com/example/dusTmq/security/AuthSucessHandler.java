package com.example.dusTmq.security;

import com.example.dusTmq.repository.member.MemberRepository;
import com.example.dusTmq.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
@Component
public class AuthSucessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberService memberService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

        memberService.updateMemberLastLogin(authentication.getName(), LocalDateTime.now());
        log.debug("updateMemberLastLogin = {} ", authentication.getName());
        setDefaultTargetUrl("/");
        super.onAuthenticationSuccess(request, response, chain, authentication);

    }
}
