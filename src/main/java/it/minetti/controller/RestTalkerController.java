package it.minetti.controller;

import io.swagger.annotations.ApiOperation;
import it.minetti.controller.model.RestTalker;
import it.minetti.controller.model.RestTalkerRequest;
import it.minetti.logic.TalksService;
import it.minetti.persistence.model.Talker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api")
public class RestTalkerController {
  private static final Logger logger = LoggerFactory.getLogger(RestTalkerController.class);

  @Autowired
  private TalksService talksService;

  @RequestMapping(value = "/talk/{name}/talkers", method = RequestMethod.GET)
  @ResponseBody
  public List<RestTalker> talkers(@PathVariable("name") String name) {
    logger.info("/talkers required for name {}", name);

    List<Talker> talkers = talksService.getAllTalkers(name);

    return talkers.stream().map(RestTalker::new).collect(Collectors.toList());
  }

  @RequestMapping(value = "/talk/{name}/talker", method = RequestMethod.POST)
  @ResponseBody
  public RestTalker create(@PathVariable String name, @RequestBody RestTalkerRequest requestTalker) {
    logger.info("/talker add for talk {} and talker {} ", name, requestTalker);

    Talker talker = talksService.addTalker(name, requestTalker);

    return new RestTalker(talker);
  }

  @RequestMapping(value = "/talk/{name}/absentTalker", method = RequestMethod.POST)
  @ResponseBody
  public RestTalker markAbsent(@PathVariable String name, @RequestBody RestTalkerRequest requestTalker) {
    logger.info("/talker absent for talk {} and talker {} ", name, requestTalker);

    Talker talker = talksService.setAbsentTalker(name, requestTalker);

    return new RestTalker(talker);
  }

  @RequestMapping(value = "/talk/{name}/talker", method = RequestMethod.GET)
  @ResponseBody
  public RestTalker randomTalker(@PathVariable String name) {
    logger.info("/talker random for talk {}", name);

    Talker talker = talksService.getRandom(name);

    return new RestTalker(talker);
  }

}
