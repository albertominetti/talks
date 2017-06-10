package it.minetti.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "event")
public class Event {
  public static final String ABSENCE = "ABSENCE";

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @NotNull
  @Column
  private String type;

  @NotNull
  @Column(name = "id_talk")
  private long idTalk;

  @NotNull
  @Column
  private String description;

  @NotNull
  @Column
  private LocalDateTime date = LocalDateTime.now();

  private Event() {
  }

  public Event(String type, long talkId, String description) {
    this.type = type;
    id = talkId;
    this.description = description;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public long getIdTalk() {
    return idTalk;
  }

  public void setIdTalk(long idTalk) {
    this.idTalk = idTalk;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }
}
