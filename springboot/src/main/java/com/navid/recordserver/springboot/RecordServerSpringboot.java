package com.navid.recordserver.springboot;

import com.navid.recordserver.springboot.controllers.LazyLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SpringBootApplication
@Configuration
@ComponentScan(basePackages = {"com.navid.recordserver.springboot"})
@ImportResource({"classpath:conf/config-main.xml", "classpath:conf/config-jmx.xml", "classpath:conf/config-couchdb-persistence.xml"})
@EnableAutoConfiguration
@PropertySource(value = {"classpath:/application.properties",
    "classpath:/conf/recordserver${env}.overrides",
    "file:${user.home}/navidconfig/recordserver${env}.overrides"}, ignoreResourceNotFound = true)
public class RecordServerSpringboot extends WebMvcConfigurerAdapter {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RecordServerSpringboot.class, args);
    }

    @Autowired
    private LazyLoginInterceptor lazyLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(lazyLoginInterceptor);
    }

}
