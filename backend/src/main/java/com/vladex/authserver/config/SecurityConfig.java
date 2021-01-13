package com.vladex.authserver.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final JdbcUserDetailsService userDetailsService;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests().antMatchers("/", "/login", "/index.html", "/sing-in", "/static/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and().csrf().disable()
        .cors().and()
        .formLogin().loginPage("/").loginProcessingUrl("/login");
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
  }
}