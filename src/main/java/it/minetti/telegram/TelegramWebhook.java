package it.minetti.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.telegram.telegrambots.api.objects.Update;

@Controller
@RequestMapping("/telegram")
public class TelegramWebhook {
  private static final Logger logger = LoggerFactory.getLogger(TelegramWebhook.class);

  @Autowired
  private TelegramHandler telegramHandler;


  @RequestMapping(value = "/", method = RequestMethod.POST)
  public ResponseEntity webhook(@RequestBody Update update) {
    logger.info("Update {} an update just arrived", update.getUpdateId());
    telegramHandler.handleUpdate(update);
    logger.info("Update {} has been processed", update.getUpdateId());
    return new ResponseEntity(HttpStatus.OK);
  }


}
