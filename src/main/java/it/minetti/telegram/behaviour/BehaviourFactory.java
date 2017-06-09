package it.minetti.telegram.behaviour;

import it.minetti.logic.TalksService;
import it.minetti.persistence.SessionRepository;
import it.minetti.persistence.model.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BehaviourFactory {
  private static final Logger logger = LoggerFactory.getLogger(BehaviourFactory.class);

  @Autowired
  private SessionRepository sessionRepository;

  @Autowired
  private TalksService talkService;

  public ChatBehaviour build(Long chatId) {
    Session session = getSession(chatId);
    return ChatBehaviour.createInstance(session, talkService);
  }

  private Session getSession(Long chatId) {
    Session session = sessionRepository.findByChatId(chatId);
    if (session == null) {
      session = new Session(chatId, null, Session.INIT);
      session = sessionRepository.save(session);
    }
    return session;
  }
}
