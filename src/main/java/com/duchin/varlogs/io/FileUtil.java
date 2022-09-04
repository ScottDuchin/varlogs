package com.duchin.varlogs.io;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/** Useful methods that deal with files. */
public final class FileUtil {

  private FileUtil() {
  }

  /**
   * Prepares an ordered set of file names from provided directory that match extension.
   * @param dir the directory to list files from
   * @param ext the extension the file name must have to be listed
   */
  @SuppressWarnings("unchecked") // empty set complaining about casting
  public static Set<String> listFileNames(File dir, String ext) {
    File[] matches = dir.listFiles((dir1, name) -> name.endsWith(ext));
    return matches == null ? Collections.EMPTY_SET
        : new TreeSet<>(Arrays.stream(matches).map(File::getName).collect(Collectors.toSet()));
  }
}
