package com.spontaneous.server;

import com.google.gson.Gson;
import com.spontaneous.server.util.GsonFactory;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class SpontaneousApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpontaneousApplication.class, args);
    }

    @Bean
    public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf) {
        return hemf.getSessionFactory();
    }

    @Bean
    public HttpMessageConverters customConverters() {
        HttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        Gson gson = GsonFactory.getGson();

        ((GsonHttpMessageConverter) gsonHttpMessageConverter).setGson(gson);

        return new HttpMessageConverters(gsonHttpMessageConverter);
    }
}
