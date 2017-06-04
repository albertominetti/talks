package it.minetti.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "talker")
public class Talker {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @ManyToOne
  @JoinColumn(name = "id_talk", insertable = false, updatable = false)
  private Talk talk;

  @NotNull
  @Column(unique = true)
  private String name;

  @Column(unique = true)
  private String email;

  @Column
  private boolean absent = false;

  public Talker(String name) {
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean isAbsent() {
    return absent;
  }

  public void setAbsent(boolean absent) {
    this.absent = absent;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("Talker{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", email='").append(email).append('\'');
    sb.append(", absent=").append(absent);
    sb.append('}');
    return sb.toString();
  }
}
