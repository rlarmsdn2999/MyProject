package org.zerock.mreview.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class securityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests().antMatchers("/main/vod").permitAll()
                .antMatchers("/movie/read").permitAll()
                .antMatchers("/movie/register").hasRole("USER")
                .antMatchers("/movie/modify").hasRole("USER")
                .antMatchers("/drama/read").permitAll()
                .antMatchers("/drama/register").hasRole("USER")
                .antMatchers("/drama/modify").hasRole("USER")
                .antMatchers("/enter/read").permitAll()
                .antMatchers("/enter/register").hasRole("USER")
                .antMatchers("/enter/modify").hasRole("USER")
                .antMatchers("/culture/read").permitAll()
                .antMatchers("/culture/register").hasRole("USER")
                .antMatchers("/culture/modify").hasRole("USER");
        http.formLogin().defaultSuccessUrl("/main/vod");
        http.csrf().disable();
        http.logout().logoutSuccessUrl("/main/vod");
        http.oauth2Login();
    }
}
