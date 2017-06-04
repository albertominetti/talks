package it.minetti.persistence;

import it.minetti.persistence.model.Talk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TalkRepository extends JpaRepository<Talk, Long> {
  Talk findByName(String test);
}