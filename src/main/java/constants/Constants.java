package constants;

import java.nio.file.FileSystems;

public class Constants {

  public static final int LONG_TIME_OUT = 15;
  public static final int SHORT_TIME_OUT = 5;
  public static final String SEPARATOR = FileSystems.getDefault().getSeparator();
  public static final String ROOT_PATH = System.getProperty("user.dir");
  public static final String RESOURCE_PATH = ROOT_PATH + SEPARATOR + "src" + SEPARATOR + "test" + SEPARATOR + "resources";
  public static final String ENVIRONMENT_PATH = RESOURCE_PATH + SEPARATOR + "environment";
  public static final String TEST_DATA = "testdata";

  private Constants() {}

  public static String getDirectorySlash(String folderName) {
    return SEPARATOR + folderName + SEPARATOR;
  }
}
