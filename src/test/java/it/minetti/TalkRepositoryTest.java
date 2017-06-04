package it.minetti;

import it.minetti.model.Talk;
import it.minetti.model.Talker;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RepositoryConfiguration.class)
@Transactional(propagation = Propagation.REQUIRED)
@ComponentScan(value = {"it.minetti"})
public class TalkRepositoryTest {
  @Autowired
  private TalkRepository talkRepository;

  @After
  public void clean() {
    talkRepository.deleteAll();
  }

  @Test
  public void findAll() {
    List<Talk> talks = talkRepository.findAll();
    assertThat(talks.size(), is(0));
  }

  @Test
  public void createAndFind() {
    Talk aTalk = new Talk("test");
    talkRepository.save(aTalk);

    Talk persistedTalk = talkRepository.findByName("test");
    assertThat(persistedTalk, is(notNullValue()));
    assertThat(persistedTalk.getName(), is(aTalk.getName()));
  }

  @Test
  public void moreTalks() {
    talkRepository.save(new Talk("test"));
    talkRepository.save(new Talk("test1"));
    talkRepository.save(new Talk("test2"));

    List<Talk> persistedTalks = talkRepository.findAll();
    assertThat(persistedTalks, is(notNullValue()));
    assertThat(persistedTalks.size(), is(3));

  }

  @Test
  public void talkWithTalkers() {
    Talk test = new Talk("test");
    test.addTalker(new Talker("Alberto"));

    talkRepository.save(test);
    Talk persistedTalk = talkRepository.findByName("test");

    assertThat(persistedTalk.getTalkers().size(), is(1));
    assertThat(persistedTalk.getTalkers().get(0).getName(), is("Alberto"));
  }


}