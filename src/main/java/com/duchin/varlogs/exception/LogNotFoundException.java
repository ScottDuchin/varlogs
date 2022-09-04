package com.duchin.varlogs.exception;

import java.io.File;

/** An exception that is thrown when the specified log file cannot be found. */
public class LogNotFoundException extends RuntimeException {

  public LogNotFoundException(String dir, String logName) {
    super(dir + File.pathSeparator + logName + " was not found");
  }
}
