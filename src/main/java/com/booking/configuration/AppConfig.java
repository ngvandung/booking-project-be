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

import com.booking.business.impl.AuthBusinessImpl;
import com.booking.business.impl.CityCategoryBusinessImpl;
import com.booking.business.impl.DistrictCategoryBusinessImpl;
import com.booking.business.impl.HomeTypeBusinessImpl;
import com.booking.business.impl.StateCategoryBusinessImpl;
import com.booking.business.impl.UserBusinessImpl;
import com.booking.business.impl.VillageCategoryBusinessImpl;
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
	public HomeTypeBusinessImpl homeTypeBusinessImpl() {
		return new HomeTypeBusinessImpl();
	}
	
	@Bean
	public CityCategoryBusinessImpl cityCategoryBusinessImpl() {
		return new CityCategoryBusinessImpl();
	}
	
	@Bean
	public DistrictCategoryBusinessImpl districtCategoryBusinessImpl() {
		return new DistrictCategoryBusinessImpl();
	}
	
	@Bean
	public StateCategoryBusinessImpl stateCategoryBusinessImpl() {
		return new StateCategoryBusinessImpl();
	}
	
	@Bean
	public VillageCategoryBusinessImpl villageCategoryBusinessImpl() {
		return new VillageCategoryBusinessImpl();
	}
	
	@Bean
	public AuthBusinessImpl authBusinessImpl() {
		return new AuthBusinessImpl();
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
