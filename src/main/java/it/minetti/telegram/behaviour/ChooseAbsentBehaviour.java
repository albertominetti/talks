package it.minetti.telegram.behaviour;

import it.minetti.logic.TalkerRequest;
import it.minetti.persistence.model.Session;
import it.minetti.persistence.model.Talk;
import it.minetti.persistence.model.Talker;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class ChooseAbsentBehaviour extends ChatBehaviour {

  ChooseAbsentBehaviour(Session session) {
    super(session);
  }

  public SendMessage sendText(String text) {
    Talk talk = talksService.getTalk(session.getTalkId());
    if (talk == null) {
      session.setStatus(Session.INIT);
      return new SendMessage(session.getChatId(), "Error! Talk not found in DB");
    } else {
      TalkerRequest talker = new TalkerRequest(text);
      talksService.setAbsentTalker(talk.getName(), talker);
      session.setStatus(Session.TALK_SELECTED);
      return new SendMessage(session.getChatId(), "Talker " + talker.getName() + " is absent");
    }
  }


  @Override
  public BotApiMethod<Message> getInitMessage() {
    SendMessage sendMessage = new SendMessage(session.getChatId(), "Write down the absent talker name");
    ReplyKeyboardMarkup replyMarkup = new ReplyKeyboardMarkup();

    List<Talker> presentTalkers = getPresentTalkers();

    List<KeyboardRow> keyboard = new ArrayList<>();

    int i = 0;
    KeyboardRow row = new KeyboardRow();
    keyboard.add(row);
    do {
      Talker talker = presentTalkers.get(i);
      row.add(talker.getName());
      i++;
      if (i > 0 && i % 3 == 0) {
        row = new KeyboardRow();
        keyboard.add(row);
      }
    } while (i < presentTalkers.size());

    replyMarkup.setKeyboard(keyboard);
    sendMessage.setReplyMarkup(replyMarkup);
    return sendMessage;
  }

  @Override
  public short getOwnStatus() {
    return Session.CHOOSE_ABSENT;
  }
}
