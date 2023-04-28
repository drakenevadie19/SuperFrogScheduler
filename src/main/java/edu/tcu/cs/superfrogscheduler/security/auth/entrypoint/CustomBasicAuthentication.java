package edu.tcu.cs.superfrogscheduler.security.auth.entrypoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

// this class will handle unsuccessful basic authentication
// if failed, commence() will be called
@Component
public class CustomBasicAuthentication implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver resolver;

    // we need exact bean handlerExceptionResolver
    public CustomBasicAuthentication(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.addHeader("WWW-authenticate", "Basic realm=\"Realm\"");
        // exception now can be hanlde in exception handler advice
        this.resolver.resolveException(request, response, null, authException);
    }

}
