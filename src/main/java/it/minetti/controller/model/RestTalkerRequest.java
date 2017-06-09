package it.minetti.controller.model;

import it.minetti.logic.TalkerRequest;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class RestTalkerRequest {

  @NotNull @NotEmpty
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
