package com.duchin.varlogs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Global exception handling for the REST controllers. */
@RestControllerAdvice
public class ExceptionAdvice {

  @ExceptionHandler(LogNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse handleNotFound(LogNotFoundException e) {
    return new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
  }

  @ExceptionHandler(NoMatchingLogEntriesException.class)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ErrorResponse handleNoMatching(NoMatchingLogEntriesException e) {
    return new ErrorResponse(HttpStatus.NO_CONTENT, e.getMessage());
  }
}
