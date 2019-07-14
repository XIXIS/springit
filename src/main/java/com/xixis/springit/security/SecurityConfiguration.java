package com.xixis.springit.security;

import com.xixis.springit.config.JWTAuthenticationEntryPoint;
import com.xixis.springit.config.JWTRequestFilter;
import com.xixis.springit.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.hibernate.criterion.Restrictions.and;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private JWTAuthenticationEntryPoint jWTAuthenticationEntryPoint;
  private UserDetailsServiceImpl userDetailsServiceImpl;
  private JWTRequestFilter jWTRequestFilter;

  public SecurityConfiguration(JWTAuthenticationEntryPoint jWTAuthenticationEntryPoint,
                               UserDetailsServiceImpl userDetailsServiceImpl,
                               JWTRequestFilter jWTRequestFilter) {
    this.jWTAuthenticationEntryPoint = jWTAuthenticationEntryPoint;
    this.userDetailsServiceImpl = userDetailsServiceImpl;
    this.jWTRequestFilter = jWTRequestFilter;
  }


  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

    // configure AuthenticationManager so that it knows from where to load
    // user for matching credentials
    // Use BCryptPasswordEncoder
    auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());

  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {


    // We don't need CSRF for this example
    http.headers().frameOptions().disable();
    http.csrf().disable()
        //public routes
        .authorizeRequests()
        .requestMatchers(EndpointRequest.to("info")).permitAll()
        .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ADMIN")
        .antMatchers("/auth/*", "/home", "/").permitAll()
        .antMatchers("/actuator/").hasRole("ADMIN")
        .antMatchers("/h2", "/h2/**").permitAll()
        // all other requests need to be authenticated
        .anyRequest().authenticated().and()
        // make sure we use stateless session; session won't be used to
        // store user's state.
        .exceptionHandling().authenticationEntryPoint(jWTAuthenticationEntryPoint).and().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);


    // Add a filter to validate the tokens with every request
    http.addFilterBefore(jWTRequestFilter, UsernamePasswordAuthenticationFilter.class);


  }

}
