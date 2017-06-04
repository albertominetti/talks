package it.minetti.controller.model;

import javax.persistence.EntityNotFoundException;

public class TalkerNotFoundException extends EntityNotFoundException {
  public TalkerNotFoundException(){
    super("Talker not found");
}
}
