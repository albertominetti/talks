package it.minetti.controller;

import it.minetti.controller.model.RestTalk;
import it.minetti.controller.model.TalkNotFoundException;
import it.minetti.persistence.EventRepository;
import it.minetti.persistence.model.Event;
import it.minetti.persistence.model.Talk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RestEventController {
  private static final Logger logger = LoggerFactory.getLogger(RestEventController.class);

  @Autowired
  private EventRepository eventRepository;

  @RequestMapping(value = "/events", method = RequestMethod.GET)
  @ResponseBody
  public List<Event> list() {
    logger.info("/talks required");
    List<Event> all = eventRepository.findAll();
    return all;
  }

}
