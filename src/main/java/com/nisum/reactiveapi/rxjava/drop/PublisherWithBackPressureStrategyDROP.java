package com.nisum.reactiveapi.rxjava.drop;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.schedulers.Schedulers;

import java.util.logging.Logger;


/**
 * BackpressureStrategy.DROP
 * here all the previous data produced by the publisher is droped.
 *
 */
public class PublisherWithBackPressureStrategyDROP {
  private final static Logger log =Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

  public static void main(String[] args) {
    Flowable.<Integer>create(emitter -> emit(emitter), BackpressureStrategy.DROP)
        .map(data -> data * 1.0)
        .filter(data -> data > 4)
        .observeOn(Schedulers.io(), false, 2)
        .subscribe(PublisherWithBackPressureStrategyDROP::printIt,
            err -> log.info("ERROR: " + err),
            () -> log.info("DONE"));
  }

  private static void printIt(Double value) throws InterruptedException {
    log.info(value + " -- " + Thread.currentThread());
    Thread.sleep(2000);
  }

  private static void emit(FlowableEmitter<Integer> emitter) throws InterruptedException {
    int count = 0;

    while(count < 20) {
      log.info("emitting " + count + " --" + Thread.currentThread());
      emitter.onNext(count++);

      Thread.sleep(500);
    }

    log.info("DONE emitting");
    Thread.sleep(10000);
  }
}