package it.minetti.persistence;

import it.minetti.persistence.model.Session;
import it.minetti.persistence.model.Talk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
  Session findByChatId(long chatId);
}