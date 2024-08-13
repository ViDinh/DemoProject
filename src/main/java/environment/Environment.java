package environment;

import org.testng.annotations.Optional;
import utils.YamlUtils;

import java.util.Map;

public class Environment {

  private static String runningEnvironment = System.getProperty("env");

  private Environment() {}

  public static String getEnvironment(@Optional("dev") String env) {
    if (runningEnvironment == null || runningEnvironment.isEmpty()) {
      System.setProperty("env", env);
      runningEnvironment = System.getProperty("env");
    }
    return runningEnvironment;
  }

  public static Map<String, Object> loadEnvironmentSetting(String filePath) {
    return YamlUtils.loadConfig(filePath);
  }
}
