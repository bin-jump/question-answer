package com.zhang.ddd.presentation.web.security;

import com.zhang.ddd.domain.util.UserPasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;


@EnableWebSecurity
@Slf4j
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                //.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/user").permitAll()
                .antMatchers("/signin", "/api/question", "/api/user/me").permitAll()
                .antMatchers("/api/**").authenticated()
                .and()
                .formLogin()
                .loginPage("/signin")
                .defaultSuccessUrl("/home")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/signout")
                .logoutSuccessUrl("/home")
                .permitAll()
                .and()
                .csrf()
                .disable()
                //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                ;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        //configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return UserPasswordEncoder.getPasswordEncoder();
    }

}
