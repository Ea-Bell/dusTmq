package com.example.dusTmq.common;

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
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMsg = "Invalid Email or Password";
        
        //exception 관련 메시지
        if (exception instanceof DisabledException) {
            errorMsg = "DisabledException account";
        } else if(exception instanceof CredentialsExpiredException){
            errorMsg = "CredentialsExpiredException account";
        } else if (exception instanceof BadCredentialsException) {
            errorMsg = "BadCredentialsException account";
        }

        setDefaultFailureUrl("/login?error=true&exception="+errorMsg);


        super.onAuthenticationFailure(request, response, exception);
    }
}
