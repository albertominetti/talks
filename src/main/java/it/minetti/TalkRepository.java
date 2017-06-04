package it.minetti;

import it.minetti.model.Talk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TalkRepository extends JpaRepository<Talk, Long> {
  Talk findByName(String test);
}