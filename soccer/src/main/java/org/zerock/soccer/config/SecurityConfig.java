package org.zerock.soccer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests().antMatchers("/soccer/list").permitAll()
                .antMatchers("/soccer/read").permitAll()
                .antMatchers("/soccer/register").hasRole("USER")
                .antMatchers("/soccer/modify").hasRole("USER")
                .antMatchers("/soccer/remove").hasRole("USER");
        http.formLogin().defaultSuccessUrl("/soccer/list");
        http.csrf().disable();
        http.logout().logoutSuccessUrl("/soccer/list");
        http.oauth2Login();
    }
}
