package it.minetti.controller;

import it.minetti.controller.model.RestEvent;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api")
public class RestEventController {
  private static final Logger logger = LoggerFactory.getLogger(RestEventController.class);

  @Autowired
  private EventRepository eventRepository;

  @RequestMapping(value = "/events", method = RequestMethod.GET)
  @ResponseBody
  public List<RestEvent> list() {
    logger.info("/talks required");
    List<Event> allEvents = eventRepository.findAll();
    allEvents = new ArrayList<>();
    return allEvents.stream().map(this::buildRestEvent).collect(Collectors.toList());
  }

  private RestEvent buildRestEvent(Event e) {
    RestEvent restEvent = new RestEvent(e.getType(), e.getIdTalk());
    restEvent.setDate(e.getDate());
    restEvent.setDescription(e.getDescription());
    return restEvent;
  }

}
