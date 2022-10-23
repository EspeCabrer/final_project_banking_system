package com.ironhack.banksystem.security;

import com.ironhack.banksystem.role.EnumRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConf) throws Exception {
        return authConf.getAuthenticationManager();
    }


    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic();

        httpSecurity.authorizeRequests()
                .mvcMatchers(HttpMethod.PATCH, "account/balance/{id}").hasRole(String.valueOf(EnumRole.ADMIN))
                .mvcMatchers(HttpMethod.POST, "thirdparty/new").hasRole(String.valueOf(EnumRole.ADMIN))
                .mvcMatchers(HttpMethod.GET,"account/new/**").hasRole(String.valueOf(EnumRole.ADMIN))
                .mvcMatchers(HttpMethod.PUT, "account/update/creditcard").hasRole(String.valueOf(EnumRole.ADMIN))
                .mvcMatchers(HttpMethod.DELETE, "account/delete/*").hasRole(String.valueOf(EnumRole.ADMIN))

//
                .mvcMatchers(HttpMethod.PATCH, "account/transfer/{id}").hasAnyRole(String.valueOf(EnumRole.ACCOUNT_HOLDER))
                .anyRequest().permitAll();

        httpSecurity.csrf().disable();

        return httpSecurity.build();
    }
}
