package it.minetti.telegram.behaviour;

import it.minetti.persistence.model.Session;
import it.minetti.persistence.model.Talk;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

public class NewTalkBehaviour extends NoTalkSelectedBehaviour {
  NewTalkBehaviour(Session session) {
    super(session);
  }

  public SendMessage sendText(String text) {
    if (talksService.existsTalk(text)) {
      session.setStatus(Session.INIT);
      return new SendMessage(session.getChatId(), "Talk already exists");
    } else {
      Talk talk = talksService.newTalk(text);
      session.setTalkId(talk.getId());
      session.setStatus(Session.TALK_SELECTED);
      return new SendMessage(session.getChatId(), "Talk " + talk.getName() + " has been creaded and chosen");
    }
  }

  @Override
  public BotApiMethod<Message> getInitMessage() {
    return new SendMessage(session.getChatId(), "Please, write down the talk name");
  }

  @Override
  public short getOwnStatus() {
    return Session.NEW_TALK;
  }


}
