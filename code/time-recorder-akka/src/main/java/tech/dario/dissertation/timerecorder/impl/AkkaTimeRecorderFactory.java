package tech.dario.dissertation.timerecorder.impl;

import tech.dario.dissertation.timerecorder.api.TimeRecorder;
import tech.dario.dissertation.timerecorder.api.TimeRecorderFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class AkkaTimeRecorderFactory implements TimeRecorderFactory {

  private final TimeRecorder timeRecorder;

  public AkkaTimeRecorderFactory() {
    timeRecorder = new AkkaTimeRecorder();
  }

  @Override
  public void start() {
    timeRecorder.start();
  }

  @Override
  public TimeRecorder getTimeRecorder() {
    return timeRecorder;
  }

  @Override
  public void stop() {
    timeRecorder.stop();
  }

  @Override
  public String toString() {
    return "AkkaTimeRecorderFactory{}";
  }
}