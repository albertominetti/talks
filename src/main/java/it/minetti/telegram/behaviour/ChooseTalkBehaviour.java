package it.minetti.telegram.behaviour;

import it.minetti.persistence.model.Session;
import it.minetti.persistence.model.Talk;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

import java.util.List;

public class ChooseTalkBehaviour extends NoTalkSelectedBehaviour {


  ChooseTalkBehaviour(Session session) {
    super(session);
  }

  public SendMessage sendText(String text) {
    if (!talksService.existsTalk(text)) {
      session.setStatus(Session.INIT);
      return new SendMessage(session.getChatId(), "No talk with name " + text);
    } else {
      Talk talk = talksService.getTalk(text);
      session.setTalkId(talk.getId());
      session.setStatus(Session.TALK_SELECTED);
      return new SendMessage(session.getChatId(), "Talk " + talk.getName() + " has been chosen");
    }
  }

  @Override
  public BotApiMethod<Message> getInitMessage() {
    List<Talk> talks = talksService.allTalks();
    StringBuilder replyText = new StringBuilder("Here you are the list of all talks: ");
    replyText.append("\n");
    for (Talk talk : talks) {
      replyText.append(talk.getName());
      replyText.append("\n");
    }
    replyText.append("Write down the talk name");
    return new SendMessage(session.getChatId(), replyText.toString());
  }

  @Override
  public short getOwnStatus() {
    return  Session.CHOOSE_TALK;
  }
}
