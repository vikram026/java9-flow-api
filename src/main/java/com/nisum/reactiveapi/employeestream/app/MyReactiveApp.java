package com.nisum.reactiveapi.employeestream.app;


import com.nisum.reactiveapi.employeestream.EmpHelper;
import com.nisum.reactiveapi.employeestream.subscriber.MySubscriber;
import com.nisum.reactiveapi.employeestream.model.Employee;

import java.util.List;
import java.util.concurrent.SubmissionPublisher;
import java.util.logging.Logger;

/**
 *
 *
 * Creating the publisher and subscriber fully by jdk 9  and using it's concept
 *
  *  the class SubmissionPublisher<T>implements Flow.Publisher<T> and it's a flexible producer of items, compliant with the Reactive Streams initiative.
 */

public class MyReactiveApp {
    private final static Logger log =Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String args[]) throws InterruptedException {
        // Create Publisher
        SubmissionPublisher<Employee> publisher = new SubmissionPublisher<>();
        // Register Subscriber
        MySubscriber subs = new MySubscriber();
        publisher.subscribe(subs);

        List<Employee> emps = EmpHelper.getEmps();

        // Publish items
        log.info("Publishing Items to Subscriber");
        emps.stream().forEach(emp -> publisher.submit(emp));

        // logic to wait till processing of all messages are over
        while (emps.size() != subs.getCounter()) {
            Thread.sleep(10);
        }
        // close the Publisher
        publisher.close();

        log.info("Exiting the app");

    }
}
