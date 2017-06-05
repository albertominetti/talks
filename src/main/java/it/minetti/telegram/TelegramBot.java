package it.minetti.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

import java.io.Serializable;
import java.util.List;

@Service
public class TelegramBot {
  private static final Logger logger = LoggerFactory.getLogger(TelegramBot.class);

  private RestTemplate restTemplate = new RestTemplate();

  @Autowired
  private BotSettings botSettings;


  public void handleUpdate(Update update) {
    Message message = update.getMessage();

    Long chatId = message.getChatId();
    String text = message.getText();

    send(new SendMessage(chatId, "Ciao - ripeto: " + text));
  }

  private void send(BotApiMethod<Message> message) {
    String url = getApiEndPoint() + message.getMethod();
    restTemplate.postForObject(url, message, Serializable.class);
  }

  private String getApiEndPoint() {
    return "https://api.telegram.org/bot" + botSettings.getToken() + "/";
  }
}
