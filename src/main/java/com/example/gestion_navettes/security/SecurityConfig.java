package com.example.gestion_navettes.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  final UserDetailsService userDetailsService;
  final PasswordEncoder passwordEncoder;
  
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.formLogin()
            .loginPage("/login")
            .usernameParameter("email")
            .passwordParameter("password")
            .failureUrl("/login?error=true")
            .defaultSuccessUrl("/")
            .permitAll();

    http.logout()
            .logoutUrl("/logout")
//            .logoutSuccessUrl("/")
            .permitAll();
    
    http.exceptionHandling()
            .accessDeniedPage("/403");
    
    http.authorizeRequests()
              .antMatchers("/", "/webjars/**", "/sign_up","/sign_up_company", "/new_client", "/new_company").permitAll()
              .antMatchers("/client/**").hasAuthority("client")
              .antMatchers("/company/**").hasAuthority("company")
              .anyRequest().authenticated();
  
    http.csrf();
    http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
  
    // nouveau
    http.authenticationProvider(authenticationProvider());
    
    return http.build();
  }
  

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder);

    return authenticationProvider;
  }
  
  
}
