package it.minetti.telegram.behaviour;

import it.minetti.persistence.model.Session;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class InitialBehaviour extends NoTalkSelectedBehaviour {
  InitialBehaviour(Session session) {
    super(session);
  }

  public SendMessage sendText(String text) {
    return null;
  }

  @Override
  public BotApiMethod<Message> getInitMessage() {
    SendMessage sendMessage = new SendMessage(session.getChatId(), "Do you want to /createTalk or /selectTalk ?");
    ReplyKeyboardMarkup replyMarkup = new ReplyKeyboardMarkup();
    List<KeyboardRow> keyboard = new ArrayList<>();
    KeyboardRow keyboardRow1 = new KeyboardRow();
    keyboardRow1.add("/createTalk");
    keyboardRow1.add("/selectTalk");
    keyboard.add(keyboardRow1);
    replyMarkup.setKeyboard(keyboard);
    sendMessage.setReplyMarkup(replyMarkup);
    return sendMessage;
  }

  @Override
  public short getOwnStatus() {
    return Session.INIT;
  }

  @Override
  public boolean changeStatus() {
    return true;
  }
}
