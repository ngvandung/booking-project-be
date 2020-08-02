/**
 * 
 */
package com.booking.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.booking.business.impl.AuthBusinessImpl;
import com.booking.business.impl.BookingBusinessImpl;
import com.booking.business.impl.CityCategoryBusinessImpl;
import com.booking.business.impl.CommentBusinessImpl;
import com.booking.business.impl.DistrictCategoryBusinessImpl;
import com.booking.business.impl.FileEntryBusinessImpl;
import com.booking.business.impl.HouseTypeBusinessImpl;
import com.booking.business.impl.HouseBusinessImpl;
import com.booking.business.impl.StateCategoryBusinessImpl;
import com.booking.business.impl.UserBusinessImpl;
import com.booking.business.impl.VillageCategoryBusinessImpl;
import com.booking.business.impl.VnPayPaymentBusinessImpl;
import com.booking.business.impl.VotingBusinessImpl;
import com.booking.scheduler.BookingScheduler;
import com.booking.scheduler.MessageQueueScheduler;
import com.booking.util.ApplicationContext;
import com.booking.util.BeanUtil;
import com.booking.util.QueryUtil;
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

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(1000000000);
		return multipartResolver;
	}

	// Create bean
	@Bean
	public UserBusinessImpl userBusinessImpl() {
		return new UserBusinessImpl();
	}
	
	@Bean
	public BookingScheduler bookingScheduler() {
		return new BookingScheduler();
	}
	
	@Bean
	public MessageQueueScheduler messageQueueScheduler() {
		return new MessageQueueScheduler();
	}
	
	@Bean
	public VnPayPaymentBusinessImpl vnPayPaymentBusinessImpl() {
		return new VnPayPaymentBusinessImpl();
	}
	
	@Bean
	public BookingBusinessImpl bookingBusinessImpl() {
		return new BookingBusinessImpl();
	}
	
	@Bean
	public QueryUtil queryUtil() {
		return new QueryUtil();
	}

	@Bean
	public CommentBusinessImpl commentBusinessImpl() {
		return new CommentBusinessImpl();
	}

	@Bean
	public VotingBusinessImpl votingBusinessImpl() {
		return new VotingBusinessImpl();
	}

	@Bean
	public FileEntryBusinessImpl fileEntryBusinessImpl() {
		return new FileEntryBusinessImpl();
	}

	@Bean
	public HouseTypeBusinessImpl houseTypeBusinessImpl() {
		return new HouseTypeBusinessImpl();
	}

	@Bean
	public HouseBusinessImpl houseBusinessImpl() {
		return new HouseBusinessImpl();
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
}
