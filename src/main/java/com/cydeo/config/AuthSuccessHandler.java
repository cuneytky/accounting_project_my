package com.cydeo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Configuration
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if(roles.contains("Root User")){
            response.sendRedirect("/companies/list");
        }

        if(roles.contains("Admin")){
            response.sendRedirect("users/list");
        }

        if(roles.contains("Manager") || roles.contains("Employee")){
            response.sendRedirect("/dashboard");
        }
    }
}
/*
Create "AuthSuccessHandler" class under "config" folder:
1- Create "AuthSuccessHandler" class under "config" folder
2- Configure landing pages after successful login
 * "Root User" should land to the "/companies/list" end point
 * "Admin" should land to the "users/list" end point
 * "Manager" and" Employee" should land to the "/dashboard" end point
 */