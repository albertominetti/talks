package it.minetti.logic;

import it.minetti.controller.model.RestTalkerRequest;
import it.minetti.controller.model.TalkNotFoundException;
import it.minetti.controller.model.TalkerNotFoundException;
import it.minetti.persistence.EventRepository;
import it.minetti.persistence.TalkRepository;
import it.minetti.persistence.model.Event;
import it.minetti.persistence.model.Talk;
import it.minetti.persistence.model.Talker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TalksService {
  private static final Logger logger = LoggerFactory.getLogger(TalksService.class);

  @Autowired
  private TalkRepository talkRepository;
  @Autowired
  private EventRepository eventRepository;

  public List<Talker> getAllTalkers(String talkName) {
    Talk talk = talkRepository.findByName(talkName);
    if (talk == null) throw new TalkNotFoundException();
    return talk.getTalkers();
  }

  public Talker addTalker(String talkName, RestTalkerRequest requestTalker) {
    Talk talk = talkRepository.findByName(talkName);
    if (talk == null) throw new TalkNotFoundException();

    Talker talker = new Talker(requestTalker.getName());
    talk.addTalker(talker);
    return talker;
  }

  public Talker setAbsentTalker(String talkName, RestTalkerRequest requestTalker) {
    Talk talk = talkRepository.findByName(talkName);
    if (talk == null) throw new TalkNotFoundException();
    Talker talker = talk.getTalkers().stream().filter(t -> t.getName().equals(requestTalker.getName()))
      .findFirst().orElseThrow(TalkerNotFoundException::new);
    talker.setAbsent(true);

    String descr = String.format("Talker %s is absent for talk %s", talker.getName(), talk.getName());
    eventRepository.save(new Event(Event.ABSENCE, talk.getId(), descr));

    return talker;
  }

  public Talker getRandom(String talkName) {
    Talk talk = talkRepository.findByName(talkName);
    if (talk == null) throw new TalkNotFoundException();

    List<Talker> presentTalkers = talk.getTalkers().stream()
      .filter(t -> !t.isAbsent()).collect(Collectors.toList());
    if (presentTalkers.size() == 0) throw new TalkerNotFoundException();

    return presentTalkers.get(new Random().nextInt(presentTalkers.size()));
  }

  public List<Talk> allTalks() {
    return talkRepository.findAll();
  }

  public Talk getTalk(String name) {
    Talk talk = talkRepository.findByName(name);
    if (talk == null) throw new TalkNotFoundException();
    return talk;
  }

  public Talk newTalk(String name) {
    Talk talk = new Talk(name);
    return talkRepository.save(talk);
  }
}
