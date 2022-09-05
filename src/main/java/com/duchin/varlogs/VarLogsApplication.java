package com.duchin.varlogs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * Main spring boot application for a simple spring boot application for retrieving logs
 * from /var/logs on local host. (pointed to in pom.xml via <start-class/>).
 */
@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(title = "Var Logs API", version = "1.0",
    description = "APIs to retrieve lines of logs within /var/log directory."
        + "\n  1) The list of available logs can be retrieved by running the /findAll API."
        + "\n  2) Lines can then be retrieved from that log by running the /readLines API."))
public class VarLogsApplication {

  /** Main method to start Spring boot application. */
  public static void main(String[] args) {
    SpringApplication.run(VarLogsApplication.class, args);
  }
}
