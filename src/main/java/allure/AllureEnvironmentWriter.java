package allure;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class AllureEnvironmentWriter {

  public static void writeEnvironmentInfo(String browser, String env) {
    Properties props = new Properties();
    props.setProperty("Browser", browser);
    props.setProperty("OS", System.getProperty("os.name"));
    props.setProperty("OS Version", System.getProperty("os.version"));
    props.setProperty("Java version", System.getProperty("java.version"));
    props.setProperty("Environment", env);

    File allureResultsDir = new File("allure-results");
    if (!allureResultsDir.exists()) {
      allureResultsDir.mkdirs();
    }

    try {
      FileUtils.writeStringToFile(new File(allureResultsDir, "environment.properties"),
          propsToString(props), "UTF-8");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static String propsToString(Properties props) {
    StringBuilder builder = new StringBuilder();
    for (String name : props.stringPropertyNames()) {
      builder.append(name).append("=").append(props.getProperty(name)).append("\n");
    }
    return builder.toString();
  }
}
