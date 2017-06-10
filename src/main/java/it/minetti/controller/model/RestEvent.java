package it.minetti.controller.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class RestEvent {
  private final String type;
  private final long idTalk;
  private String description;
  private LocalDateTime date;

  public RestEvent(String type, long idTalk) {

    this.type = type;
    this.idTalk = idTalk;
  }

  public String getType() {
    return type;
  }

  public long getIdTalk() {
    return idTalk;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
  public LocalDateTime getDate() {
    return date;
  }
}
