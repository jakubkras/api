package com.GymApl.Security;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {


@Autowired
    UserDetailsServiceImplementation userDetailsServiceImplementation;

@Autowired
    AuthenticationEntryPoint authenticationEntryPoint;


@Bean
   public AuthTokenFilter authTokenFilter (){
    return new AuthTokenFilter();
}

@Bean
    public DaoAuthenticationProvider authenticationProvider (){
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsServiceImplementation);
    authenticationProvider.setPasswordEncoder(passwordEncoder());

    return authenticationProvider;
}

@Bean
    public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
}

@Bean
    AuthenticationManager authenticationManager (AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
}

@Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
    http.
            csrf().disable().
            authorizeHttpRequests(auth ->
                    auth.requestMatchers("/login").permitAll().
                            anyRequest().permitAll());
    http.formLogin(Customizer.withDefaults()).
    httpBasic(Customizer.withDefaults());

    http.authenticationProvider(authenticationProvider());
    http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
}

}

