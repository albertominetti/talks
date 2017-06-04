package it.minetti.controller.model;

import it.minetti.model.Talk;

public class RestTalk {

  private final String name;
  private final int totalTalkers;

  public RestTalk(Talk t) {
    this.name = t.getName();
    this.totalTalkers = t.getTalkers().size();
  }

  public String getName() {
    return name;
  }

  public int getTotalTalkers() {
    return totalTalkers;
  }
}
