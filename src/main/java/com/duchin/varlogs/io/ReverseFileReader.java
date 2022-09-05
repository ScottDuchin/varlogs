package com.duchin.varlogs.io;

import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

/**
 * A file reader that return lines in reverse order to have the most recent lines
 * returned first as they are the most relevant to review.
 */
public class ReverseFileReader {

  private static final int MIN_BLOCK_SIZE = 1024;

  @Value("${var-log.read.block-size}")
  private int BLOCK_SIZE = 4096;

  private final RandomAccessFile randomAccessFile;

  /**
   * Constructor.
   * @param file the file to read lines from in reverse order
   * @throws FileNotFoundException if the file cannot be processed
   */
  public ReverseFileReader(File file)
      throws FileNotFoundException {
    randomAccessFile = new RandomAccessFile(file, "r");
    if (BLOCK_SIZE < MIN_BLOCK_SIZE) {
      BLOCK_SIZE = MIN_BLOCK_SIZE; // minimum size for a block
    }
  }

  /**
   * Reads lines from the end of the file in reverse order.
   * @param maxLines the maximum number of lines to be returned
   * @param pattern if provided, the lines returned must match the regex pattern provided
   * @throws IOException if any problems occur while reading the log file
   */
  public List<String> readLinesReverse(int maxLines, @Nullable Pattern pattern)
      throws IOException {
    List<String> lines = new ArrayList<>();
    long cursor = randomAccessFile.length();
    if (cursor == 0) {
      return lines;
    }
    byte[] block = new byte[BLOCK_SIZE];
    // read and process blocks starting at end of file
    for (;;) {
      // move cursor back to start of previous block to read
      int readMax = (int) Math.min(BLOCK_SIZE, cursor);
      long seekPosition = cursor - readMax;
      randomAccessFile.seek(seekPosition);
      int bytesRead = randomAccessFile.read(block, 0, readMax);
      if (bytesRead == 0) {
        break;
      }
      // TODO(sduchin): profile performance of scanning raw byte[] vs converting to String
      String blockText = new String(block, 0, bytesRead, StandardCharsets.UTF_8);
      String[] splits = blockText.split("\n");
      // the 0th index will only be considered a complete line if seekPosition at start of file;
      // could tell if it is a complete line within middle of file by peeking at seekPosition - 1
      for (int i = splits.length - 1; i > 0 || i == 0 && seekPosition == 0; i--) {
        if (pattern == null || pattern.matcher(splits[i]).find()) {
          lines.add(splits[i]);
        }
        if (lines.size() >= maxLines) {
          return lines;
        }
        cursor -= splits[i].length() + 1; // line + linefeed
      }
    }
    return lines;
  }
}
