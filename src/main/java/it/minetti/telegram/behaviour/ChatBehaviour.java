package it.minetti.telegram.behaviour;

import it.minetti.logic.TalksService;
import it.minetti.persistence.model.Session;
import it.minetti.persistence.model.Talk;
import it.minetti.persistence.model.Talker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.*;

public abstract class ChatBehaviour {

  private static final Logger logger = LoggerFactory.getLogger(BehaviourFactory.class);

  protected final Session session;
  transient protected TalksService talksService;

  protected ChatBehaviour(Session session) {
    this.session = session;
  }

  public void setTalksService(TalksService talksService) {
    this.talksService = talksService;
  }

  public void start() {
    session.setStatus(Session.INIT);

  }

  public void newTalk() {
    session.setStatus(Session.NEW_TALK);
    session.setTalkId(null);
  }

  public SendMessage addTalker() {
    session.setStatus(Session.WAITING_TALKER);
    return null;
  }

  public SendMessage absent() {
    session.setStatus(Session.CHOOSE_ABSENT);
    return null;
  }

  public SendMessage random() {
    Talk talk = talksService.getTalk(session.getTalkId());
    Talker random = talksService.getRandom(talk.getName());
    return new SendMessage(session.getChatId(), "Here is a random presenter " + random.getName());
  }

  public abstract SendMessage sendText(String text);

  public SendMessage select() {
    List<Talk> talks = talksService.allTalks();
    if (talks.size() > 0) {
      session.setStatus(Session.CHOOSE_TALK);
    } else {
      return new SendMessage(session.getChatId(), "Still no chat is available");
    }
    return null;
  }

  public short getStatusId() {
    return session.getStatus();
  }

  public SendMessage list() {
    Talk talk = talksService.getTalk(session.getTalkId());
    StringBuilder replyText = new StringBuilder("Here you are the list of all talkers: ");
    replyText.append("\n");
    for (Talker talker : talk.getTalkers()) {
      replyText.append(talker.getName());
      if (talker.isAbsent()) {
        replyText.append(" [a]");
      }
      replyText.append("\n");
    }
    return new SendMessage(session.getChatId(), replyText.toString());
  }

  public abstract BotApiMethod<Message> getInitMessage();

  public final ChatBehaviour next() {
    return createInstance(session, talksService);
  }

  public static ChatBehaviour createInstance(Session session, TalksService talksService) {

    ChatBehaviour chatBehaviour = null;

    switch (session.getStatus()) {
      case Session.INIT:
        chatBehaviour = new InitialBehaviour(session);
        break;
      case Session.NEW_TALK:
        chatBehaviour = new NewTalkBehaviour(session);
        break;
      case Session.CHOOSE_TALK:
        chatBehaviour = new ChooseTalkBehaviour(session);
        break;

      case Session.TALK_SELECTED:
        chatBehaviour = new TalkSelectedBehaviour(session);
        break;
      case Session.WAITING_TALKER:
        chatBehaviour = new AddTalkerBehaviour(session);
        break;
      case Session.CHOOSE_ABSENT:
        chatBehaviour = new ChooseAbsentBehaviour(session);
        break;
      default:
        logger.error("There is no ChatBehaviour associated to " + session.getStatus());
        chatBehaviour = new InitialBehaviour(session);
        break;
    }

    chatBehaviour.setTalksService(talksService);

    return chatBehaviour;
  }

  public abstract short getOwnStatus();

  public boolean changeStatus() {
    return session.getStatus() != getOwnStatus();
  }
}
