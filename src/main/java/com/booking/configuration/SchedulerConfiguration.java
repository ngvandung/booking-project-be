/**
 * 
 */
package com.booking.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * @author ddung
 *
 */
@Configuration
public class SchedulerConfiguration implements SchedulingConfigurer {
	private final ThreadPoolTaskScheduler taskScheduler;

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskScheduler);
	}

	public SchedulerConfiguration() {
		taskScheduler = new ThreadPoolTaskScheduler();

		taskScheduler.setThreadNamePrefix("@scheduler-");

		taskScheduler.initialize();
	}

	public ThreadPoolTaskScheduler getTaskScheduler() {
		return taskScheduler;
	}

	private static class SingletonHelper {
		private static final SchedulerConfiguration INSTANCE = new SchedulerConfiguration();
	}

	public static SchedulerConfiguration getInstance() {
		return SingletonHelper.INSTANCE;
	}

}
