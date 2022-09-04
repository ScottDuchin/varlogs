package com.duchin.varlogs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Global exception handling for the REST controllers. */
@RestControllerAdvice
public class ExceptionAdvice {

  @ExceptionHandler(NoLogLinesException.class)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ErrorResponse handleNoLines(NoLogLinesException e) {
    return new ErrorResponse(HttpStatus.NO_CONTENT, e.getMessage());
  }

  @ExceptionHandler(LogNotAuthorizedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ErrorResponse handleNotAuthorized(LogNotAuthorizedException e) {
    return new ErrorResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
  }

  @ExceptionHandler(LogNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse handleNotFound(LogNotFoundException e) {
    return new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleValidation(IllegalArgumentException e) {
    return new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
  }
}
