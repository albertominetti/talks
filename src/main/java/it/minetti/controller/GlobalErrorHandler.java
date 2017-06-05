package it.minetti.controller;

import it.minetti.controller.model.ApiError;
import it.minetti.controller.model.TalkNotFoundException;
import it.minetti.controller.model.TalkerNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalErrorHandler {
  private final static Logger logger = LoggerFactory.getLogger(GlobalErrorHandler.class);

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public ApiError handleException(EntityNotFoundException e) {
    return new ApiError(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e.getMessage());
  }

}
