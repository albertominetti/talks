package it.minetti.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "talk")
public class Talk implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @NotNull
  @Column(unique = true)
  private String name;

  @NotNull
  private String description = "";

  @OneToMany(mappedBy = "talk", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Talker> talkers = new ArrayList<>();

  public Talk(String name) {
    this.name = name;
  }

  public void addTalker(Talker talker) {
    this.talkers.add(talker);
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Talker> getTalkers() {
    return talkers;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("Talk{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", description='").append(description).append('\'');
    sb.append('}');
    return sb.toString();
  }

}
