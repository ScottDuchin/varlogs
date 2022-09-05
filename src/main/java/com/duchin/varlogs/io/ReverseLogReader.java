package com.duchin.varlogs.io;

import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
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

  private static final int MIN_BLOCK_SIZE = 1024;

  @Value("${var-log.read.block-size}")
  private int BLOCK_SIZE = 4096;

  private final RandomAccessFile raf;

  /**
   * Constructor.
   * @param logFile the log file to read lines from in reverse order
   * @throws FileNotFoundException if the log file cannot be processed
   */
  public ReverseLogReader(File logFile)
      throws FileNotFoundException {
    raf = new RandomAccessFile(logFile, "r");
    if (BLOCK_SIZE < MIN_BLOCK_SIZE) {
      BLOCK_SIZE = MIN_BLOCK_SIZE; // minimum size for a block
    }
  }

  /**
   * Reads lines from the end of the file in reverse order.
   * @param maxLines the maximum number of lines to be returned
   * @param regex if provided, the lines returned will match the regex provided
   * @throws IOException if any problems occur while reading the log file
   */
  public List<String> readLinesReverse(int maxLines, @Nullable String regex)
      throws IOException {
    // TODO(sduchin): make sure regex matches if not null
    List<String> lines = new ArrayList<>();
    long cursor = raf.length();
    if (cursor == 0) {
      return lines;
    }
    byte[] block = new byte[BLOCK_SIZE];
    // read and process blocks starting at end of file
    for (;;) {
      // move cursor back to start of previous block to read
      int readMax = (int) Math.min(BLOCK_SIZE, cursor);
      raf.seek(cursor - readMax);
      int bytesRead = raf.read(block, 0, readMax);
      if (bytesRead == 0) {
        break;
      }
      // TODO(sduchin): profile performance of scanning raw byte[] vs converting to String
      String blockText = new String(block, 0, bytesRead, StandardCharsets.UTF_8);
      String[] splits = blockText.split("\n");
      for (int i = splits.length - 1; i > 0; i--) {
        lines.add(splits[i]);
        if (lines.size() >= maxLines) {
          return lines;
        }
        cursor -= splits[i].length();
      }
      // the last splits is probably a partial line, make sure it is not end of file else circle
      if (bytesRead < BLOCK_SIZE) {
        lines.add(splits[0]);
        break; // read the file
      }
    }
    return lines;
  }
}
