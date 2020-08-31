package com.nisum.reactiveapi.coldpublisher;

import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Cold publisher will have defferent different session for different subscribers;
 */
public class ColdPublisherConcept {

  private final static Logger log =Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

  public static void main(String[] args) throws InterruptedException {
    Flowable<Long> interval =
        Flowable.interval(1, TimeUnit.SECONDS);

    interval.subscribe(data -> printMessage("s1:" + data));

    Thread.sleep(5000);

    interval.subscribe(data -> printMessage("s2:" + data));

    Thread.sleep(10000);
  }

  private static void printMessage(String message) {
    log.info(message);
  }
}