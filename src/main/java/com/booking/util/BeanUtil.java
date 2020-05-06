/**
 * 
 */
package com.booking.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author ddung
 *
 */
public class BeanUtil implements ApplicationContextAware {
	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

	public static <T> T getBean(Class<T> beanClass) {
		return context.getBean(beanClass);
	}
}
