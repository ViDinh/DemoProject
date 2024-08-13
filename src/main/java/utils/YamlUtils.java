package utils;

import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class YamlUtils {

  private YamlUtils() {}

  public static Map<String, Object> loadConfig(String filePath) {
    Map<String, Object> data = new HashMap<>();
    try {
      InputStream inputStream = new FileInputStream(filePath);
      Yaml yaml = new Yaml();
      data = yaml.load(inputStream);
    } catch (FileNotFoundException e) {
      log.error("File not found ", e);
    }
    return data;
  }
}
