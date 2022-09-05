package com.duchin.varlogs.io;

import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

/**
 * A log file reader that return lines in reverse order to have the most recent lines
 * returned first as they are the most relevant to review.
 * TODO(sduchin): this class was written as instructed in a job interview assignment,
 *                should probably be replaced with apache commons io ReversedLinesFileReader
 *                NOTE: the commons io class WAS NOT reviewed or used as a template for this one
 */
public class ReverseLogReader {

  @Value("${var-log.read.block-size}")
  private long BLOCK_SIZE = 4096;

  private final RandomAccessFile raf;

  /**
   * Constructor.
   * @param logFile the log file to read lines from in reverse order
   * @throws FileNotFoundException if the log file cannot be processed
   */
  public ReverseLogReader(File logFile)
      throws FileNotFoundException {
    raf = new RandomAccessFile(logFile, "r");
  }

  /**
   * Reads lines from the end of the file in reverse order.
   * @param maxLines the maximum number of lines to be returned
   * @param regex if provided, the lines returned will match the regex provided
   * @throws IOException if any problems occur while reading the log file
   */
  public List<String> readLinesReverse(int maxLines, @Nullable String regex)
      throws IOException {
    List<String> lines = new ArrayList<>();
    long cursor = raf.length();
    return lines;
  }
}
