package com.nisum.reactiveapi.employeestream.app;

import com.nisum.reactiveapi.employeestream.EmpHelper;
import com.nisum.reactiveapi.employeestream.processor.MyProcessor;
import com.nisum.reactiveapi.employeestream.model.Employee;
import com.nisum.reactiveapi.employeestream.subscriber.MySubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.SubmissionPublisher;

/**
 * Doing some transformation
 * the class SubmissionPublisher<T>implements Flow.Publisher<T> and it's a flexible producer of items, compliant with the Reactive Streams initiative.
 */
public class MyReactiveAppWithProcessor {
	private static final Logger log =
			LoggerFactory.getLogger(MyReactiveAppWithProcessor.class);
	public static void main(String[] args) throws InterruptedException {
		// Create End Publisher
		SubmissionPublisher<Employee> publisher = new SubmissionPublisher<>();

		// Create Processor adding 100 to the id;
		MyProcessor transformProcessor = new MyProcessor(s -> {
			return new Employee(s.getId()+100, s.getName());
		});

		//Create End Subscriber
		MySubscriber subs = new MySubscriber();

		//Create chain of publisher, processor and subscriber
		publisher.subscribe(transformProcessor); // publisher to processor
		transformProcessor.subscribe(subs); // processor to subscriber

		List<Employee> emps = EmpHelper.getEmps();

		// Publish items
		log.info("Publishing Items to Subscriber");
		emps.stream().forEach(i -> publisher.submit(i));

		// Logic to wait for messages processing to finish
		while (emps.size() != subs.getCounter()) {
			Thread.sleep(10);
		}

		// Closing publishers
		publisher.close();
		transformProcessor.close();

		log.info("Exiting the app");
	}

}