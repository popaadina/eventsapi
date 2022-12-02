package com.app.eventsmanagementapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventsmanagementapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventsmanagementapiApplication.class, args);
	}

//	@Bean
//	public FilterRegistrationBean<AuthFilter> authFilter(){
//		FilterRegistrationBean<AuthFilter> registrationBean
//				= new FilterRegistrationBean<>();
//
//		registrationBean.setFilter(new AuthFilter());
//		registrationBean.addUrlPatterns("/api/auth/*");
//		registrationBean.setOrder(2);
//
//		return registrationBean;
//	}

}
