package com.example.springapp;

import com.example.springapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    UserRepository userRepository;
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            @Transactional
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                com.example.springapp.entity.User user = userRepository.findByUsername(username);
                // JDBC Driver Registration
                // Database Connection
                // Query Execute
                // ResultSet To Data
                // Connection Close
                if (user == null) {
                    throw new UsernameNotFoundException(
                            "No user found with username: " + username);
                }
                return User.builder().username(username).password(passwordEncoder()
                        .encode(user.getPassword())).roles("ADMIN").build();
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().disable().httpBasic().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests()
                .antMatchers("/api/v1/student").hasRole("User")
                .anyRequest().authenticated();
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() throws Exception {
        return web -> web.ignoring().antMatchers("/api/v1/user/register","/api/v1/user/login");
    }
}
