package com.security.jwtdemo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.security.jwtdemo.config.filter.JwtAuthFilter;
import com.security.jwtdemo.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private MyUserDetailsService myUserDetailsService;


  @Autowired
  private JwtAuthFilter jwtAuthFilter;
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests()
        .antMatchers("/auth", "/actuator/health", "/h2-console", "/styles").permitAll().anyRequest()
        .authenticated().and().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    
    http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
  }


  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(myUserDetailsService);
  }

  @Bean
  public PasswordEncoder passcodeEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    // this needs to given spring boot 2.0 to create default auth manager bean
    return super.authenticationManagerBean();
  }
}
