package it.minetti.telegram.behaviour;

import it.minetti.logic.TalkerRequest;
import it.minetti.persistence.model.Session;
import it.minetti.persistence.model.Talk;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

public class AddTalkerBehaviour extends ChatBehaviour {

  AddTalkerBehaviour(Session session) {
    super(session);
  }

  public SendMessage sendText(String text) {
    if (!talksService.existsTalk(session.getTalkId())) {
      session.setStatus(Session.INIT);
      return new SendMessage(session.getChatId(), "Error! Talk not found in DB");
    } else {
      Talk talk = talksService.getTalk(session.getTalkId());
      TalkerRequest talker = new TalkerRequest(text);
      talksService.addTalker(talk.getName(), talker);
      session.setStatus(Session.TALK_SELECTED);
      return new SendMessage(session.getChatId(), "Talker " + talker.getName() + " has been added");
    }
  }

  @Override
  public BotApiMethod<Message> getInitMessage() {
    return new SendMessage(session.getChatId(), "Write down the talker name");
  }

  @Override
  public short getOwnStatus() {
    return Session.WAITING_TALKER;
  }
}
