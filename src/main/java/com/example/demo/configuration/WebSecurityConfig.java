package com.example.demo.configuration;

import com.example.demo.repositories.TestRepository;
import com.example.demo.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthProvider authenticationProvider;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(authenticationProvider)
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.httpBasic();
        http
                .authorizeRequests()
                .antMatchers("/login/**")
                .permitAll()
                .anyRequest()
                .authenticated();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var userDetailsService =
                new InMemoryUserDetailsManager();
        var user = User.withUsername("test")
                .password("12345")
                .authorities("read")
                .build();
        userDetailsService.createUser(user);
        return userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }

    class CustomUserProvider implements UserDetailsManager {

        private TestRepository repo;

        @Override
        public void createUser(UserDetails user) {

        }

        @Override
        public void updateUser(UserDetails user) {

        }

        @Override
        public void deleteUser(String username) {

        }

        @Override
        public void changePassword(String oldPassword, String newPassword) {

        }

        @Override
        public boolean userExists(String username) {
            return false;
        }

        public void some() {}

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            var user = User.withUsername("test")
                    .password("12345")
                    .authorities("read")
                    .build();
            return new SecurityUser((User) user);
        }
    }

}
