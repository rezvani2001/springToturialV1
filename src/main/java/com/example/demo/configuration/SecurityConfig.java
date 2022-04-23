package com.example.demo.configuration;

import com.example.demo.Models.security.BasicAuthentication;
import com.example.demo.Models.security.CookieAuthentication;
import com.example.demo.services.AuthService;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new BasicAuthentication(AuthService.instance), BasicAuthenticationFilter.class)
                .addFilterBefore(new CookieAuthentication(AuthService.instance), BasicAuthentication.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/v1/auth/login").permitAll()
//                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .logout().permitAll().deleteCookies("auth");
    }
}
