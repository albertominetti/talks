package it.minetti.view;

import it.minetti.logic.TalkerRequest;
import it.minetti.logic.TalksService;
import it.minetti.persistence.model.Talk;
import it.minetti.persistence.model.Talker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Scope("session")
@Component
public class ViewTalk {
  private static final Logger logger = LoggerFactory.getLogger(ViewTalk.class);

  @Autowired
  private transient TalksService talksService;

  private String newTalkName;
  private String newTalkerName;
  private String absentTalkerName;
  private String randomTalker;
  private String selectedTalkName;

  public List<Talk> getAllTalks() {
    try {
      return talksService.allTalks();
    } catch (Exception ee) {
      addError("Error: " + ee.getMessage());
    }
    return Collections.emptyList();
  }

  public List<String> getAvailableTalks() {
    return getAllTalks().stream().map(Talk::getName).collect(Collectors.toList());
  }

  public void createNewTalk() {
    try {
      talksService.newTalk(this.getNewTalkName());
      addInfo("Talk created!");
    } catch (Exception ee) {
      addError("Error: " + ee.getMessage());
    }
  }

  public void addTalker() {
    try {
      TalkerRequest requestTalker = new TalkerRequest(this.getNewTalkerName());
      talksService.addTalker(this.getSelectedTalkName(), requestTalker);
      addInfo("Talker created!");
    } catch (Exception ee) {
      addError("Error: " + ee.getMessage());
    }
  }


  public List<Talker> getPresentTalkers() {
    return getAllTalkers().stream().filter(t -> !t.isAbsent()).collect(Collectors.toList());
  }


  public void absentTalker() {
    try {
      TalkerRequest requestTalker = new TalkerRequest(this.getAbsentTalkerName());
      talksService.setAbsentTalker(this.getSelectedTalkName(), requestTalker);
      addInfo("Talker is now absent!");
    } catch (Exception ee) {
      addError("Error: " + ee.getMessage());
    }
  }

  public void random() {
    try {
      Talker random = talksService.getRandom(this.getSelectedTalkName());
      this.setRandomTalker(random.getName());
      addInfo("Presenter has been choosen!");
    } catch (Exception ee) {
      addError("Error: " + ee.getMessage());
    }
  }

  public Integer getCount() {
    if (StringUtils.isEmpty(this.getSelectedTalkName())) return null;
    return talksService.getTalk(this.getSelectedTalkName()).getTalkers().size();
  }

  /*
   * simple Getters and Setters
   */

  public String getNewTalkName() {
    return newTalkName;
  }

  public void setNewTalkName(String newTalkName) {
    this.newTalkName = newTalkName;
  }


  public String getNewTalkerName() {
    return newTalkerName;
  }

  public void setNewTalkerName(String newTalkerName) {
    this.newTalkerName = newTalkerName;
  }

  public void setAbsentTalkerName(String absentTalkerName) {
    this.absentTalkerName = absentTalkerName;
  }

  public String getAbsentTalkerName() {
    return absentTalkerName;
  }

  public void setRandomTalker(String randomTalker) {
    this.randomTalker = randomTalker;
  }

  public String getRandomTalker() {
    return randomTalker;
  }

  public String getSelectedTalkName() {
    return selectedTalkName;
  }

  public void setSelectedTalkName(String selectedTalkName) {
    logger.info("setSelectedTalkName({})", selectedTalkName);
    this.selectedTalkName = selectedTalkName;
  }

  /*
   * private methods
   */

  private List<Talker> getAllTalkers() {
    if (StringUtils.isEmpty(this.getSelectedTalkName())) return Collections.emptyList();

    Talk talk = talksService.getTalk(this.getSelectedTalkName());
    return talk.getTalkers();
  }

  private void addError(String text) {
    FacesContext.getCurrentInstance().addMessage(null,
      new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", text));
  }

  private void addInfo(String text) {
    FacesContext.getCurrentInstance().addMessage(null,
      new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", text));
  }

}
