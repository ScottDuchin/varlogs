package com.duchin.varlogs.io;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

class ReverseFileReaderTest {

  private static final ClassLoader CLASS_LOADER = ReverseFileReaderTest.class.getClassLoader();
  private static final Pattern PATTERN = Pattern.compile("Statistics");

  @Test
  public void readLinesReverse_abc()
      throws Exception {
    URL resource = CLASS_LOADER.getResource("abc.txt");
    File systemTxt = Paths.get(resource.toURI()).toFile();
    ReverseFileReader reverseFileReader = new ReverseFileReader(systemTxt);
    List<String> lines = reverseFileReader.readLinesReverse(100, null);
    assertThat(lines).containsExactly("xyz", "def", "abc").inOrder();
  }

  @Test
  public void readLinesReverse_noRegex()
      throws Exception {
    URL resource = CLASS_LOADER.getResource("system.txt");
    File systemTxt = Paths.get(resource.toURI()).toFile();
    ReverseFileReader reverseFileReader = new ReverseFileReader(systemTxt);
    List<String> lines = reverseFileReader.readLinesReverse(100, null);
    assertThat(lines.size()).isEqualTo(89);
  }

  @Test
  public void readLinesReverse_regex()
      throws Exception {
    URL resource = CLASS_LOADER.getResource("system.txt");
    File systemTxt = Paths.get(resource.toURI()).toFile();
    ReverseFileReader reverseFileReader = new ReverseFileReader(systemTxt);
    List<String> lines = reverseFileReader.readLinesReverse(100, PATTERN);
    assertThat(lines.size()).isEqualTo(39);
  }

  @Test
  public void readLinesReverse_zero()
      throws Exception {
    URL resource = CLASS_LOADER.getResource("zero.txt");
    File zeroTxt = Paths.get(resource.toURI()).toFile();
    ReverseFileReader reverseFileReader = new ReverseFileReader(zeroTxt);
    List<String> lines = reverseFileReader.readLinesReverse(100, null);
    assertThat(lines).isEmpty();
  }
}
