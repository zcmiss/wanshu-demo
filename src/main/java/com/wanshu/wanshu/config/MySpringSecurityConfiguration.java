package com.wanshu.wanshu.config;

import com.wanshu.wanshu.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class MySpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/flowable-task/app-api/**","/login.html","/css/**","/js/**","/img/**","/fonts/**","/docs/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                // 登录
                .and()
                .formLogin()
                .loginPage("/login.html")
                // 登录表单提交的认证地址
                .loginProcessingUrl("/loginUrl")
                .defaultSuccessUrl("/home.html")
                .permitAll()
                .and()
                .logout()
                .and()
                .csrf().disable()
                // 针对 布局内嵌禁用放开
                .headers().frameOptions().disable()
        ;
    }
}
