package it.minetti.controller.model;

import it.minetti.model.Talk;

public class RestTalk {

  private final String name;

  public RestTalk(Talk t) {
    this.name = t.getName();
  }

  public String getName() {
    return name;
  }
}
