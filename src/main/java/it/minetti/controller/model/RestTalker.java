package it.minetti.controller.model;

import it.minetti.persistence.model.Talker;

public class RestTalker {
  private final String name;
  private final boolean absent;

  public RestTalker(Talker t) {
    this.name = t.getName();
    this.absent = t.isAbsent();
  }

  public String getName() {
    return name;
  }

  public boolean isAbsent() {
    return absent;
  }
}
