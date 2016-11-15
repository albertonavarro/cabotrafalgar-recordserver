package com.navid.recordserver.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@Configuration
@ComponentScan(basePackages = {"com.navid.recordserver.springboot", "com.navid.spring.addons"})
@ImportResource({"classpath:conf/config-main.xml", "classpath:conf/config-jmx.xml", "classpath:conf/config-couchdb-persistence.xml"})
@EnableAutoConfiguration
@PropertySource(value = {"classpath:/application.properties",
    "classpath:/conf/recordserver${env}.overrides",
    "file:${user.home}/navidconfig/recordserver${env}.overrides"}, ignoreResourceNotFound = true)
public class RecordServerSpringboot {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RecordServerSpringboot.class, args);
    }
    

}
