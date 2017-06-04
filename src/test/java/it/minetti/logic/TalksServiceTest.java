package it.minetti.logic;

import it.minetti.controller.model.RestTalkerRequest;
import it.minetti.controller.model.TalkNotFoundException;
import it.minetti.persistence.EventRepository;
import it.minetti.persistence.TalkRepository;
import it.minetti.persistence.model.Event;
import it.minetti.persistence.model.Talk;
import it.minetti.persistence.model.Talker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
public class TalksServiceTest {

  private final String TALK_NAME = "talk";
  @Mock
  private TalkRepository talkRepository;

  @Mock
  private EventRepository eventRepository;

  @InjectMocks
  private TalksService talksService;

  @Test
  public void getAllTalkers() {
    when(talkRepository.findByName(TALK_NAME)).thenReturn(new Talk(TALK_NAME));
    talksService.getAllTalkers(TALK_NAME);
    verify(talkRepository).findByName(TALK_NAME);
  }

  @Test(expected = TalkNotFoundException.class)
  public void getAllTalkersNotFound() {
    when(talkRepository.findByName(TALK_NAME)).thenReturn(null);
    talksService.getAllTalkers(TALK_NAME);
  }

  @Test
  public void addTalker() {
  }

  @Test
  public void setAbsentTalker() {
    Talk t = new Talk(TALK_NAME);
    t.addTalker(new Talker("Alberto"));
    when(talkRepository.findByName(TALK_NAME)).thenReturn(t);

    talksService.setAbsentTalker(TALK_NAME, new RestTalkerRequest(){{this.setName("Alberto");}});
    verify(talkRepository).findByName(TALK_NAME);
    verify(eventRepository).save(any(Event.class));
  }

  @Test
  public void getRandom() {
  }

  @Test
  public void allTalks() {
  }

  @Test
  public void getTalk() {
  }

  @Test
  public void newTalk() {
  }

}