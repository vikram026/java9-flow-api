package com.nisum.reactiveapi.employeestream.processor;

import com.nisum.reactiveapi.employeestream.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Flow.Processor;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Function;

/**
 * The interface Flow.Publisher<T> defines methods to produce items and control signals.
 * The interface Flow.Subscriber<T> defines methods to receive those messages and signals.
 * The interface Flow.Subscription defines the methods to link both the Publisher and the Subscriber.
 * The interface Flow.Processor<T,R> defines methods to do some advanced operations like chaining transformations of items from publishers to subscribers.
 */

public class MyProcessor extends SubmissionPublisher<Employee> implements Processor<Employee, Employee> {
	private static final Logger log =
			LoggerFactory.getLogger(MyProcessor.class);

	private Subscription subscription;
	private Function<Employee,Employee> function;
	
	public MyProcessor(Function<Employee,Employee> function) {
	    super();  
	    this.function = function;
	  }  
	
	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		subscription.request(2);
	}

	@Override
	public void onNext(Employee emp) {
		submit(function.apply(emp)   );
	    subscription.request(1);  
	}

	@Override
	public void onError(Throwable e) {
		e.printStackTrace();
	}

	@Override
	public void onComplete() {
		log.info("Done");
	}

}