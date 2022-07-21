package com.mywebsite.webbanhang.login_register.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.mywebsite.webbanhang.login_register.service.CustomOAuth2UserService;
import com.mywebsite.webbanhang.login_register.successHandler.OAuth2SuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private CustomOAuth2UserService oathAuth2UserService;

    @Autowired
    private OAuth2SuccessHandler authenticationSuccessHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers(
                        "/admin/**",
                        "/admin/js/**",
                        "/admin/css/**",
                        "/admin/img/**",
                        "/admin/vendor/**",
                        "/login/**")
                .access("hasRole('ROLE_ADMIN')")
                .antMatchers(
                        "/client/cart/**", "/login/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
                .antMatchers(
                        "/registration**",
                        "/**",
                        "/css/**",
                        "/fonts/**",
                        "/img/**",
                        "/js/**",
                        "/admin/js/**",
                        "/admin/css/**",
                        "/admin/img/**",
                        "/admin/vendor/**",
                        "/login/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                        .loginPage("/login")
                        .permitAll()
                .and()
                .oauth2Login()
                        .loginPage("/login")
                        .userInfoEndpoint()
                        .userService(oathAuth2UserService)
                        .and()
                                .successHandler(authenticationSuccessHandler)
                .and()
                .logout()
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                .and();

        return http.build();
    }

}