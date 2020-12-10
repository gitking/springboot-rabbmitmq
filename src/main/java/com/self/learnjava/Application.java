package com.self.learnjava;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Application 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(Application.class, args);
    }
    
    @Bean
    MessageConverter createMessageConverter() {
    	return new Jackson2JsonMessageConverter();
    }
    
    @Bean
    WebMvcConfigurer createWebMvcConfigurer(@Autowired HandlerInterceptor[] interceptors) {
    	return new WebMvcConfigurer(){
    		@Override
    		public void addResourceHandlers(ResourceHandlerRegistry registry) {
    			registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    		}
    	};
    }
}
