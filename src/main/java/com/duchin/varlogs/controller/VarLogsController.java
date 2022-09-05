package com.duchin.varlogs.controller;

import com.duchin.varlogs.service.VarLogsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;

/** REST controller for processing requests related to /var/logs directory log files. */
@RestController("/varlogs")
public class VarLogsController {

  @Value("${var-log.read.max-lines}")
  private long MAX_LINES = 5000;

  private final VarLogsService varLogsService;

  public VarLogsController(VarLogsService varLogsService) {
    this.varLogsService = varLogsService;
  }

  /** Finds all logs available for retrieving lines from in the /var/log directory. */
  @Operation(summary = "Finds all available logs in the /var/log directory on this host")
  // TODO(sduchin): track down swagger bug where api responses from readLines showing up here
  @ApiResponses({
      @ApiResponse(responseCode = "200")
  })
  @GetMapping(value = "/findAll", produces = "application/json")
  public Set<String> findAll() {
    return varLogsService.findAll();
  }

  /**
   * Read lines from specified named log in the /var/log directory.
   * @param logName the log within /var/log to read lines from
   * @param maxLines the maximum number of lines to be returned, default: 100.
   *                 this value cannot exceed 5000 else will result in a bad request
   * @param regex if provided, the lines returned will match the regex provided
   * TODO(sduchin): add date brackets, 'since' and 'until', to filter lines, can use for paging
   * TODO(sduchin): add page number as not all logs have dates
   *                save name, cursor, log, regex, and user during previous read in short term cache
   */
  @Operation(summary = "Read lines from specified named log in the /var/log directory on this host")
  @ApiResponses({
      @ApiResponse(responseCode = "200"),
      @ApiResponse(responseCode = "204", description = "either log is empty or no lines matched",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "400", description = "bad request due to parameter validation",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "401", description = "log file read is unauthorized",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "404", description = "log file not found",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "500", description = "log file had read failure",
          content = @Content(mediaType = "application/json"))
  })
  @GetMapping(value = "/readLines/{logName}", produces = "application/json")
  public List<String> readLines(@PathVariable String logName,
      @RequestParam(required = false, defaultValue = "100") int maxLines,
      @Nullable @RequestParam(required = false) String regex) {
    if (maxLines > MAX_LINES) {
      throw new IllegalArgumentException("maxLines exceeded API limit of " + MAX_LINES);
    }
    return varLogsService.readLinesReverse(logName, maxLines, regex);
  }
}
