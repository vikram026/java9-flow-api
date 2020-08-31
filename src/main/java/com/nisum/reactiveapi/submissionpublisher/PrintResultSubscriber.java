package com.nisum.reactiveapi.submissionpublisher;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.logging.Logger;

/**
 * creating the subscriber implementing the Flow.Subscriber given by jdk 9
 * here we are maintaining the flow of data as well using request method of Subscription Interface;;
 */

public class PrintResultSubscriber implements Subscriber<String> {
  private Subscription subscription;
  private final static Logger log =Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


  //initially user is asking for the no of initial data
  @Override
  public void onSubscribe(Subscription subscription) {
    log.info("subscribed...");
    this.subscription = subscription;
    subscription.request(5);
  }

  @Override
  public void onNext(String data) {
    log.info(data);
    subscription.request(1);
    if(data.equals("CoronaPatient9")) subscription.cancel();
  }

  @Override
  public void onError(Throwable throwable) {

  }

  @Override
  public void onComplete() {
    
  }
}