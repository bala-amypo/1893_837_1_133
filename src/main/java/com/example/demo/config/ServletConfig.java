package com.example.demo.config;

import com.example.demo.servlet.SimpleStatusServlet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

@Configuration
public class ServletConfig {

    @Bean
    public ServletRegistrationBean<SimpleStatusServlet> simpleStatusServlet() {
        return new ServletRegistrationBean<>(
                new SimpleStatusServlet(),
                "/simple-status"
        );
    }
}
