package com.kshrd.tnakrean.configuration.security;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.kshrd.tnakrean.service.serviceImplementation.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    JwtAuthenticationEntryPoint jwtEntryPoint;

    // override three methods
    final
    UserServiceImp userServiceImp;

    public WebSecurityConfig(@Lazy UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServiceImp)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizeJson() {
        return builder -> {
            builder.indentOutput(true);
            builder.propertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE);
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeRequests().antMatchers("/api/v1/auth/**").permitAll()
                .antMatchers("/api/v1/teacher/**"
                        ,"/api/v1/submittedWork/**",
                        "/api/v1/class/**", "/api/v1/classroom/**", "/api/v1/classMaterialsType/**", "/api/v1/classMaterial/**",
                        "/api/v1/v/**"
                )
                .hasAnyAuthority("Teacher")


                .antMatchers("/api/v1/student/**"
                        , "api/v1/comment/**","api/v1/submittableWork/**","/api/v1/submittedWork/get-by-studentId",
                        "/api/v1/submittedWork/get-by-studentId-and-classId",
                        "/api/v1/classMaterial/get-by-studentId", "get-by-studentId-classId-classroomId"
                )
                .hasAnyAuthority("Student");


//        This is for the jwt
        http.addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public JwtRequestFilter jwtRequestFilter() {
        return new JwtRequestFilter();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    //    ignore resources
    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/swagger-ui/**", "/v3/api-docs/**");
    }


}