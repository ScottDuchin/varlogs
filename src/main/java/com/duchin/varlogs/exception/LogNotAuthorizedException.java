package com.duchin.varlogs.exception;

import java.io.File;

/** An exception that is thrown when the specified log file cannot be read. */
public class LogNotAuthorizedException extends RuntimeException {

  public LogNotAuthorizedException(String dir, String logName) {
    super(dir + File.pathSeparator + logName + " is not authorized to read");
  }
}
