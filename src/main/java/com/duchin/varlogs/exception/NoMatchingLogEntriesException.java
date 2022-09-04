package com.duchin.varlogs.exception;

import java.io.File;

/**
 * An exception that is thrown when a log file is either empty
 * or no entries match the filter criteria.
 */
public class NoMatchingLogEntriesException extends RuntimeException {

  public NoMatchingLogEntriesException(String dir, String logName) {
    super(dir + File.pathSeparator + logName + " had no matching entries");
  }
}
