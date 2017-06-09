package it.minetti.telegram.behaviour;

import it.minetti.persistence.model.Session;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class TalkSelectedBehaviour extends ChatBehaviour {
  TalkSelectedBehaviour(Session session) {
    super(session);
  }

  public SendMessage sendText(String text) {
    return null;
  }

  @Override
  public BotApiMethod<Message> getInitMessage() {
    SendMessage sendMessage = new SendMessage(session.getChatId(), "You can /markAbsent /addTalker /random /list");    ReplyKeyboardMarkup replyMarkup = new ReplyKeyboardMarkup();
    List<KeyboardRow> keyboard = new ArrayList<>();
    KeyboardRow keyboardRow1 = new KeyboardRow();
    keyboardRow1.add("/addTalker");
    keyboardRow1.add("/list");
    keyboard.add(keyboardRow1);
    KeyboardRow keyboardRow2 = new KeyboardRow();
    keyboardRow2.add("/markAbsent");
    keyboardRow2.add("/random");
    keyboard.add(keyboardRow2);
    replyMarkup.setKeyboard(keyboard);
    sendMessage.setReplyMarkup(replyMarkup);
    return sendMessage;
  }

  @Override
  public short getOwnStatus() {
    return Session.TALK_SELECTED;
  }

}
