package com.nisum.reactiveapi.rxjava.error;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;

import java.util.logging.Logger;

/**
 * handling error using the reactively;
 * Exceptions and the functional programming are mutually exclusive;
 * exceptions are imparative programming idea
 * so in reactive api it  deals it with downstream;
 *
 */
public class ErrorHandlingConcept {
  private final static Logger log =Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

  public static void main(String[] args) {
    Flowable.<Integer>create(emitter -> emit(emitter), BackpressureStrategy.BUFFER)
        .map(data -> data * 1.0)
        .filter(data -> data > 4)
        .subscribe(System.out::println,
            err -> log.info("ERROR: " + err),
            () -> log.info("DONE"));
  }

  private static void emit(FlowableEmitter<Integer> emitter) throws InterruptedException {
    int count = 0;

    while(count < 20) {
      emitter.onNext(count++);

      if(count == 7) throw new RuntimeException("something went wrong");
      Thread.sleep(500);
    }
  }
}