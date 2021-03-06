package com.example.dusTmq.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
@Slf4j
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            log.debug("AuthFailureHandler.onAuthenticationFailure()");

        String msg = "Invalid Email or Password";

        //exception 관련 메시지 처리
        if (exception instanceof DisabledException) {
            msg = "DisabledException account";
        } else if (exception instanceof CredentialsExpiredException) {
            msg = "CredentialsExpiredException account";
        } else if (exception instanceof BadCredentialsException) {
            msg = "badCredentialsException account";
        }
        setDefaultFailureUrl("/login?error=true&exception=" + msg);


        super.onAuthenticationFailure(request, response, exception);
    }
}
