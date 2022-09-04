package com.duchin.varlogs.service;

import com.duchin.varlogs.exception.LogNotAuthorizedException;
import com.duchin.varlogs.exception.LogNotFoundException;
import com.duchin.varlogs.exception.NoLogLinesException;
import com.duchin.varlogs.io.FileUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;

/** The service responsible for all business logic associated with retrieving logs from /var. */
@Service
public class VarLogsService {

  public static final String VAR_LOG = "/var/log";

  /** Finds all logs available for retrieving information in the /var/log directory. */
  public Set<String> findAll() {
    return FileUtil.listFileNames(new File(VAR_LOG), ".log");
  }

  /**
   * Read lines from specified named log in the /var/log directory.
   * @param logName the log within /var/log to read lines from
   * @param maxLines the maximum number of lines to be returned
   * @param regex if provided, the lines returned will match the regex provided
   */
  public List<String> readLines(String logName, int maxLines, @Nullable String regex) {
    File logFile = new File(VAR_LOG, logName);
    if (!logFile.exists()) {
      throw new LogNotFoundException(VAR_LOG, logName);
    }
    if (!logFile.canRead()) {
      throw new LogNotAuthorizedException(VAR_LOG, logName);
    }
    if (logFile.length() == 0) {
      throw new NoLogLinesException(VAR_LOG, logName);
    }
    // TODO(sduchin): replace with a reverse line reader
    ArrayList<String> lines = new ArrayList<>();
    if (lines.isEmpty()) {
      throw new NoLogLinesException(VAR_LOG, logName);
    }
    return lines;
  }
}
