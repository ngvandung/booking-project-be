/**
 * 
 */
package com.booking.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.booking.model.Counter;
import com.booking.repository.CounterRepository;
import com.booking.service.CounterService;

/**
 * @author ddung
 *
 */
@Service
public class CounterServiceImpl implements CounterService {

	@Resource
	private CounterRepository counterRepository;

	@Override
	public long increment(String clazz) {
		Counter counter = counterRepository.findById(clazz);
		long count = 0;
		if (counter == null) {
			counter = new Counter();
			counter.setClazz(clazz);
			counter.setCounter(1);
			counter = counterRepository.createCounter(counter);

			if (counter != null) {
				count = counter.getCounter();
			}
		} else {
			counter.setCounter(counter.getCounter() + 1);
			counter = counterRepository.updateCounter(counter);
			if (counter != null) {
				count = counter.getCounter();
			}
		}
		return count;
	}

}
