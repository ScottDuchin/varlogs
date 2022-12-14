package com.duchin.varlogs.io;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Set;

class FileUtilTest {

  public static final File VAR_LOG_DIR = new File("/var/log");

  @Test
  public void listFileNames_varLogs() {
    Set<String> logs = FileUtil.listFileNames(VAR_LOG_DIR, ".log");
    assertThat(logs.size()).isAtLeast(10);
  }
}