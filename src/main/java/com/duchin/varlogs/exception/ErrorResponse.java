package com.duchin.varlogs.exception;

import org.springframework.http.HttpStatus;

/** Special error response if the request could not be performed as requested. */
public class ErrorResponse {

  private final String message;
  private final int statusCode;
  private final String statusName;

  public ErrorResponse(HttpStatus httpStatus, String message) {
    this.message = message;
    statusCode = httpStatus.value();
    statusName = httpStatus.name();
  }

  public String getMessage() {
    return message;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public String getStatusName() {
    return statusName;
  }
}