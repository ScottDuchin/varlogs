package com.duchin.varlogs.exception;

import java.io.File;

/** An exception that is thrown when the specified log file had a read failure. */
public class LogReadFailedException extends RuntimeException {

  public LogReadFailedException(String dir, String logName) {
    super(dir + File.pathSeparator + logName + " had a failure while reading");
  }
}
