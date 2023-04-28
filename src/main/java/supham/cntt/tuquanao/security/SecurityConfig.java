package supham.cntt.tuquanao.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import supham.cntt.tuquanao.exception.ResponseExceptionsHandler;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

  @Autowired
  AuthEntryPointJwt authEntryPointJwt;

  @Value("${supham.expiredTime}")
  private int cookieExpirationTime;

  @Bean
  PasswordEncoder getBCryptPasswordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

  @Bean
  public AccessDeniedHandler accessDeniedHandler() {
    return new ResponseExceptionsHandler();
  }

  @Bean
  public JwtAuthenticateFilter authenticationJwtTokenFilter() {
    return new JwtAuthenticateFilter();
  }

  @Bean
  public AuthenticationManager authManager(
      HttpSecurity http, PasswordEncoder noOpPasswordEncoder, UserDetailsService userDetailService)
      throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
        .userDetailsService(userDetailService)
        .passwordEncoder(noOpPasswordEncoder)
        .and()
        .build();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.cors()
        .and()
        .csrf()
        .disable()
        .exceptionHandling()
        .accessDeniedHandler(accessDeniedHandler())
        .authenticationEntryPoint(authEntryPointJwt)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.authorizeRequests()
        .antMatchers(
            "/login", "/register", "/product/list-new", "/product/list-pro", "/product/detail",
            "/product/list-lq", "/size", "/category", "/donation-form/save")
        .permitAll().anyRequest().authenticated();
    http.rememberMe().key("uniqueAndSecret").tokenValiditySeconds(this.cookieExpirationTime);
    http.addFilterBefore(
        authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
