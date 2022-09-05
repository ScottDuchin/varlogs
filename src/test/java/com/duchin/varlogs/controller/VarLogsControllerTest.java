package com.duchin.varlogs.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static com.google.common.truth.Truth.assertThat;

import java.util.List;
import java.util.Set;

@SpringBootTest
class VarLogsControllerTest {

  @Autowired
  private VarLogsController varLogsController;

  @Test
  void findAll() {
    Set<String> logs = varLogsController.findAll();
    assertThat(logs.size()).isAtLeast(10);
    assertThat(logs).contains("system.log");
  }

  @Test
  void readLines() {
    List<String> lines = varLogsController.readLines("system.log", 10, null);
    assertThat(lines.size()).isEqualTo(10);
  }
}