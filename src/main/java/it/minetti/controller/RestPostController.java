package it.minetti.controller;

import it.minetti.TalkRepository;
import it.minetti.controller.model.RestTalk;
import it.minetti.controller.model.RestTalker;
import it.minetti.controller.model.TalkerNotFoundException;
import it.minetti.model.Talk;
import it.minetti.model.Talker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
@Transactional(propagation = Propagation.REQUIRED)
public class RestPostController {
  private static final Logger logger = LoggerFactory.getLogger(RestPostController.class);

  @Autowired
  private TalkRepository talkRepository;

  @RequestMapping(value = "/talk/{name}", method = RequestMethod.POST)
  @ResponseBody
  public RestTalk create(@PathVariable String name) {
    logger.info("/talk create for name {}", name);

    Talk talk = new Talk(name);
    Talk stored = talkRepository.save(talk);

    return new RestTalk(stored);
  }

  @RequestMapping(value = "/talk/{name}/talker", method = RequestMethod.POST)
  @ResponseBody
  public RestTalker create(@PathVariable String name, @RequestBody String talkerName) {
    logger.info("/talker add for talk {} and talker {} ", name, talkerName);

    Talk talk = talkRepository.findByName(name);

    Talker talker = new Talker(talkerName);
    talk.addTalker(talker);

    return new RestTalker(talker);
  }

  @RequestMapping(value = "/talk/{name}/absentTalker", method = RequestMethod.POST)
  @ResponseBody
  public RestTalker markAbsent(@PathVariable String name, @RequestBody String talkername) {
    logger.info("/talker absent for talk {} and talker {} ", name, talkername);

    Talk talk = talkRepository.findByName(name);

    Talker talker = talk.getTalkers().stream().filter(t -> t.getName().equals(talkername)).findFirst().orElseThrow(TalkerNotFoundException::new);
    talker.setAbsent(true);

    return new RestTalker(talker);
  }

  @RequestMapping(value = "/talk/{name}/talker", method = RequestMethod.GET)
  @ResponseBody
  public RestTalker randomTalker(@PathVariable String name) {
    logger.info("/talker random for talk {}", name);

    Talk talk = talkRepository.findByName(name);

    List<Talker> presentTalkers = talk.getTalkers().stream().filter(t -> !t.isAbsent()).collect(Collectors.toList());
    Talker talker = presentTalkers.get(new Random().nextInt(presentTalkers.size()));

    return new RestTalker(talker);
  }

}
