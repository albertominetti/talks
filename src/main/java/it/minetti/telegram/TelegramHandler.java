package it.minetti.telegram;

import it.minetti.telegram.behaviour.ChatBehaviour;
import it.minetti.telegram.behaviour.BehaviourFactory;
import it.minetti.telegram.behaviour.InitialBehaviour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

@Service
public class TelegramHandler {
  private static final Logger logger = LoggerFactory.getLogger(TelegramHandler.class);

  private RestTemplate restTemplate = new RestTemplate();

  @Autowired
  private BotSettings botSettings;

  @Autowired
  private BehaviourFactory behaviourFactory;

  @Transactional(propagation = Propagation.REQUIRED)
  public void handleUpdate(Update update) {
    Message message = update.getMessage();

    long chatId = message.getChatId();
    ChatBehaviour currentStatus = behaviourFactory.build(chatId);
    logger.info("[{}] Current status is {} and behaviour is {}", chatId,
      currentStatus.getStatusId(), currentStatus.getClass().getSimpleName());

    String text = message.getText();
    logger.info("[{}] Handling update with text is {}", chatId, text);
    if (text.startsWith("/")) {
      commandHandle(currentStatus, text);
    } else {
      freeTextHandle(currentStatus, text);
    }

    if (currentStatus.changeStatus()) {
      currentStatus = currentStatus.next();
      send(currentStatus.getInitMessage());
    } else {
      logger.info("[{}] Next status is {} and behaviour is {}", chatId,
        currentStatus.getStatusId(), currentStatus.getClass().getSimpleName());
    }
  }

  private void commandHandle(ChatBehaviour currentStatus, String text) {
    switch (text) {
      case "/start":
      case "/reset":
        currentStatus.start();
        break;
      case "/createTalk":
      case "/new":
        currentStatus.newTalk();
        break;
      case "/selectTalk":
      case "/select":
        send(currentStatus.select());
        break;
      case "/addTalker":
        send(currentStatus.addTalker());
        break;
      case "/list":
        send(currentStatus.list());
        break;
      case "/markAbsent":
        send(currentStatus.absent());
        break;
      case "/random":
        send(currentStatus.random());
        break;
      default:
        logger.warn("Unknown command " + text);
        break;
    }
  }

  private void freeTextHandle(ChatBehaviour currentStatus, String text) {
    send(currentStatus.sendText(text));
  }

  public Message send(BotApiMethod<Message> message) {
    if (message == null) return null;
    String url = getApiEndPoint() + message.getMethod();
    return restTemplate.postForObject(url, message, Message.class);
  }

  private String getApiEndPoint() {
    return "https://api.telegram.org/bot" + botSettings.getToken() + "/";
  }
}
