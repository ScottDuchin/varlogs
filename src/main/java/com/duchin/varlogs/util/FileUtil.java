package com.duchin.varlogs.util;

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

  /** Prepares a set of file names from provided directory that have provided extension. */
  @SuppressWarnings("unchecked") // empty set complaining about casting
  public static Set<String> fetchFileNames(File dir, String extension) {
    File[] matches = dir.listFiles((dir1, name) -> name.endsWith(extension));
    return matches == null ? Collections.EMPTY_SET
        : new TreeSet<>(Arrays.stream(matches).map(File::getName).collect(Collectors.toSet()));
  }
}
