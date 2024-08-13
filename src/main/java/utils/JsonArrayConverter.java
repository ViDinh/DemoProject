package utils;

import java.util.List;

public class JsonArrayConverter {

  private JsonArrayConverter() {}

  public static <T> Object[][] covertJsonFileToObjectArray(String filePath, Class<T> clzz) {
    List<T> results = JacksonUtils.deserializationJsonResourceToList(filePath, clzz);
    return results.stream().map(item -> new Object[] {item}).toArray(Object[][]::new);
  }
}
