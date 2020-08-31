package com.nisum.reactiveapi.employeestream.subscriber;


import com.nisum.reactiveapi.employeestream.model.Employee;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.logging.Logger;

/**
 * Subscriber using back pressure
 *
 *  * The interface Flow.Publisher<T> defines methods to produce items and control signals.
 *  * The interface Flow.Subscriber<T> defines methods to receive those messages and signals.
 *  * The interface Flow.Subscription defines the methods to link both the Publisher and the Subscriber.
 *  * The interface Flow.Processor<T,R> defines methods to do some advanced operations like chaining transformations of items from publishers to subscribers.
 */
public class MySubscriber implements Subscriber<Employee> {
    private final static Logger log =Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private Subscription subscription;
    private int counter = 0;

    @Override
    public void onSubscribe(Subscription subscription) {
        log.info("Subscribed");
        this.subscription = subscription;
        this.subscription.request(2); //requesting data from publisher
        log.info("onSubscribe requested 2 item");
    }

    @Override
    public void onNext(Employee item) {
        log.info("Atrer Processing Employee " + item);
        counter++;
        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable e) {
        log.info("Some error happened");
        e.printStackTrace();
    }
    @Override
    public void onComplete() {
        log.info("All Processing Done");
    }

    public int getCounter() {
        return counter;
    }
}
