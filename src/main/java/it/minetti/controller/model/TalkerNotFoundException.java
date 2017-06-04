package it.minetti.controller.model;

public class TalkerNotFoundException extends RuntimeException {
  public TalkerNotFoundException(){
    super("Talker not found");
}
}
