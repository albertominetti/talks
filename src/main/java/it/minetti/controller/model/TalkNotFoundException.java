package it.minetti.controller.model;

public class TalkNotFoundException extends RuntimeException {
  public TalkNotFoundException(){
    super("Talk not found");
  }
}
