package it.minetti.controller;

import it.minetti.logic.TalksService;
import it.minetti.persistence.TalkRepository;
import it.minetti.controller.model.RestTalk;
import it.minetti.controller.model.TalkNotFoundException;
import it.minetti.persistence.model.Talk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class RestTalkController {
  private static final Logger logger = LoggerFactory.getLogger(RestTalkController.class);

  @Autowired
  private TalksService talksService;

  @RequestMapping(value = "/talks", method = RequestMethod.GET)
  @ResponseBody
  public Set<String> list() {
    logger.info("/talks required");

    List<Talk> all = talksService.allTalks();
    return all.stream().map(Talk::getName).collect(Collectors.toSet());
  }

  @RequestMapping(value = "/talk/{name}", method = RequestMethod.GET)
  @ResponseBody
  public RestTalk talk(@PathVariable("name") String name) {
    logger.info("/talk required for name {}", name);
    Talk talk = talksService.getTalk(name);
    return new RestTalk(talk);
  }

  @RequestMapping(value = "/talk/{name}", method = RequestMethod.PUT)
  @ResponseBody
  public RestTalk create(@PathVariable String name) {
    logger.info("/talk create for name {}", name);
    Talk talk = talksService.newTalk(name);
    return new RestTalk(talk);
  }

}
