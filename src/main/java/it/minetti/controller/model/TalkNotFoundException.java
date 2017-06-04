package it.minetti.controller.model;

import javax.persistence.EntityNotFoundException;

public class TalkNotFoundException extends EntityNotFoundException {
  public TalkNotFoundException(){
    super("Talk not found");
  }
}
