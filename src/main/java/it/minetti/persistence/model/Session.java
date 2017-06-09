package it.minetti.persistence.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "session")
public class Session implements Serializable {
  public static final short INIT = 0;
  public static final short NEW_TALK = 1;
  public static final short CHOOSE_TALK = 2;
  public static final short TALK_SELECTED = 3;
  public static final short WAITING_TALKER = 4;
  public static final short CHOOSE_ABSENT = 5;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @NotNull
  @Column(unique = true)
  private long chatId;

  @Column(name = "talk_id")
  private Long talkId;

  @NotNull
  @Column
  private short status = INIT;

  private Session() {
  }

  public Session(long chatId, Long talkId, short status) {
    this.chatId = chatId;
    this.talkId = talkId;
    this.status = status;
  }

  public long getChatId() {
    return chatId;
  }

  public void setChatId(long chatId) {
    this.chatId = chatId;
  }

  public Long getTalkId() {
    return talkId;
  }

  public void setTalkId(Long talkId) {
    this.talkId = talkId;
  }

  public short getStatus() {
    return status;
  }

  public void setStatus(short status) {
    this.status = status;
  }
}
