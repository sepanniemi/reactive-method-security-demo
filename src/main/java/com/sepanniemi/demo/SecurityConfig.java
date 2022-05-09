package com.sepanniemi.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
        UserDetails minor = userBuilder.username("minor")
                .password("minor")
                .authorities("lemonades.read")
                .build();
        UserDetails user = userBuilder.username("user")
                .password("user")
                .authorities("beers.read")
                .build();
        UserDetails admin = userBuilder.username("admin")
                .password("admin")
                .authorities("beers.read","beers.write")
                .build();
        return new MapReactiveUserDetailsService(minor, user, admin);
    }

    @Bean
    SecurityWebFilterChain springWebFilterChain(
            ServerHttpSecurity http,
            DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler,
            PermissionService permissionService){
        defaultMethodSecurityExpressionHandler.setPermissionEvaluator(permissionService);
        //default allow permit all and let method security authorise
        return http
                .authorizeExchange(exchanges ->
                        exchanges
                                .anyExchange().permitAll()
                )
                .httpBasic(withDefaults())
                .build();
    }
}
