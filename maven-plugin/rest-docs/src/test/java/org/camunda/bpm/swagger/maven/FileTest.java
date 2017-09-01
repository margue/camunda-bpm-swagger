package org.camunda.bpm.swagger.maven;

import org.junit.Test;

import java.io.File;
import java.nio.file.Files;

public class FileTest {

  @Test
  public void name() throws Exception {


    File foo = new File("target/tmp/myFile.xaml");

    foo.getParentFile().mkdirs();
    foo.createNewFile();


    foo.getParentFile().mkdirs();
    foo.createNewFile();
  }
}
