/**
 * 
 */
package com.booking.repository;

import com.booking.model.Counter;

/**
 * @author ddung
 *
 */
public interface CounterRepository {
	public Counter createCounter(Counter counter);
	public Counter updateCounter(Counter counter);
	public Counter findById(String clazz);
}
