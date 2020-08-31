package com.nisum.reactiveapi.rxjava.synchronous;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;

import java.util.logging.Logger;

//sync by default
public class SynchronousFlow {
  private final static Logger log =Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

  public static void main(String[] args) {
    Flowable.<Integer>create(emitter -> emit(emitter), BackpressureStrategy.BUFFER)
        .map(data -> data * 1.0)
        .filter(data -> data > 4)
        .subscribe(SynchronousFlow::printIt,
            err -> log.info("ERROR: " + err),
            () -> log.info("DONE"));
  }

  private static void printIt(Double value) {
    log.info(value + " -- " + Thread.currentThread());
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