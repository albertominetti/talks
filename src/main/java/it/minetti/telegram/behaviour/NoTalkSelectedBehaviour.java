package it.minetti.telegram.behaviour;

import it.minetti.persistence.model.Session;
import org.telegram.telegrambots.api.methods.send.SendMessage;

public abstract class NoTalkSelectedBehaviour extends ChatBehaviour {
  NoTalkSelectedBehaviour(Session session) {
    super(session);
  }

  @Override
  public SendMessage addTalker() {
    return new SendMessage(session.getChatId(), "No talk has been selected");
  }

  @Override
  public SendMessage absent() {
    return new SendMessage(session.getChatId(), "No talk has been selected");
  }

  @Override
  public SendMessage random() {
    return new SendMessage(session.getChatId(), "No talk has been selected");
  }

  @Override
  public SendMessage list() {
    return new SendMessage(session.getChatId(), "No talk has been selected");
  }

}
