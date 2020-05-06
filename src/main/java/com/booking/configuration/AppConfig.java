/**
 * 
 */
package com.booking.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.booking.business.impl.UserBusinessImpl;
import com.booking.security.PermissionCheckerFactoryUtil;
import com.booking.util.ApplicationContext;
import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.booking")
public class AppConfig implements WebMvcConfigurer {
	@Bean
	public ViewResolver viewResolver() {
		System.out.println("======> AppConfig");
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");

		return viewResolver;
	}

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/resources/");
	}

	// Create bean
	@Bean
	public UserBusinessImpl userBusinessImpl() {
		return new UserBusinessImpl();
	}

	@Bean
	public UserContext userContext() {
		return new UserContext();
	}

	@Bean
	public BeanUtil beanUtil() {
		return new BeanUtil();
	}

	@Bean
	public ApplicationContext applicationContext() {
		return new ApplicationContext();
	}

	@Bean
	public PermissionCheckerFactoryUtil permissionCheckerFactoryUtil() {
		return new PermissionCheckerFactoryUtil();
	}
}
