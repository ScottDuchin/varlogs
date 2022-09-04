package com.duchin.varlogs.service;

import com.duchin.varlogs.exception.LogNotFoundException;
import com.duchin.varlogs.exception.NoMatchingLogEntriesException;
import com.duchin.varlogs.util.FileUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/** The service responsible for all business logic associated with retrieving logs from /var. */
@Service
public class VarLogsService {

  public static final String VAR_LOG = "/var/log";

  /** Returns a set of logs available for retrieving information from. */
  public Set<String> findLogs() {
    return FileUtil.fetchFileNames(new File(VAR_LOG), ".log");
  }

  /** Lists entries of specified log name. */
  public List<String> list(String logName) {
    File logFile = new File(VAR_LOG, logName);
    if (!logFile.exists()) {
      throw new LogNotFoundException(VAR_LOG, logName);
    }
    if (logFile.length() == 0) {
      throw new NoMatchingLogEntriesException(VAR_LOG, logName);
    }
    ArrayList<String> lines = new ArrayList<>();
    if (lines.isEmpty()) {
      throw new NoMatchingLogEntriesException(VAR_LOG, logName);
    }
    return lines;
  }
}
