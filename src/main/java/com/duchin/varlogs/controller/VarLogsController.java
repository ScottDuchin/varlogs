package com.duchin.varlogs.controller;

import com.duchin.varlogs.service.VarLogsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/** REST controller for processing requests related to /var/logs log files. */
@RestController("/varlogs")
public class VarLogsController {

  private final VarLogsService varLogsService;

  public VarLogsController(VarLogsService varLogsService) {
    this.varLogsService = varLogsService;
  }

  /** Finds logs available for retrieving information from. */
  @Operation(summary = "Finds all available logs in the /var/log directory on this host")
  @ApiResponse(responseCode = "200")
  @GetMapping(value = "/findLogs", produces = "application/json")
  public Set<String> findLogs() {
    return varLogsService.findLogs();
  }

  /** Lists entries of specified log name. */
  @Operation(summary = "Lists entries of specified named log from /var/log directory on this host")
  @ApiResponses({
      @ApiResponse(responseCode = "200"),
      @ApiResponse(responseCode = "204", description = "either log is empty or no entries matched"),
      @ApiResponse(responseCode = "404", description = "log file not found")
  })
  @GetMapping(value = "/list/{logName}", produces = "application/json")
  public List<String> list(@PathVariable String logName) {
    return varLogsService.list(logName);
  }
}
