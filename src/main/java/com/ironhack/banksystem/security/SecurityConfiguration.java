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

    @Autowired
    CustomUserDetailsService customUserDetailsService;

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
                .mvcMatchers(HttpMethod.PATCH, "account/balance/{id}").hasAnyRole(String.valueOf(EnumRole.ADMIN))
                .mvcMatchers(HttpMethod.POST, "thirdparty/new").hasAnyRole(String.valueOf(EnumRole.ADMIN))
                .mvcMatchers(HttpMethod.PATCH, "account/transfer/{id}").hasAnyRole(String.valueOf(EnumRole.ACCOUNT_HOLDER))
                //.mvcMatchers(HttpMethod.GET, "/account/**").hasAnyRole(String.valueOf(EnumRole.ACCOUNT_HOLDER))
              //  .mvcMatchers(HttpMethod.GET,"/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll();

        httpSecurity.csrf().disable();

        return httpSecurity.build();

    }

}
