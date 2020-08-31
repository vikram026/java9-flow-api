package com.nisum.reactiveapi.rxjava.asynchronous;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.schedulers.Schedulers;

import java.util.logging.Logger;

/**
 * creating asynchronous subscriber
 * by putting delay
 * Here back pressure is handled using BackpressureStrategy.BUFFER
 */
public class AysnchronousConcepts {
  private final static Logger log =Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

  public static void main(String[] args) {
    Flowable.<Integer>create(emitter -> emit(emitter), BackpressureStrategy.BUFFER)
        .map(data -> data * 1.0)
        .filter(data -> data > 4)
        .observeOn(Schedulers.io())
        .subscribe(AysnchronousConcepts::printIt,
            err -> log.info("ERROR: " + err),
            () -> log.info("DONE"));
  }

  private static void printIt(Double value) throws InterruptedException {
    log.info(value + " -- " + Thread.currentThread());
    Thread.sleep(1000);
  }

  private static void emit(FlowableEmitter<Integer> emitter) throws InterruptedException {
    int count = 0;

    while(count < 20) {
      log.info("emitting " + count + " --" + Thread.currentThread());
      emitter.onNext(count++);

      Thread.sleep(500);
    }
  }
}