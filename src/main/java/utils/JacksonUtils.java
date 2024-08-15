package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class JacksonUtils {

  @Getter private static final ObjectMapper objectMapper = initializeObjectMapper();

  private static ObjectMapper initializeObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return mapper;
  }

  public static <T> List<T> deserializationJsonFileToList(File file, Class<T> clzz)
      throws IOException {
    List<Map<String, Object>> list =
        objectMapper.readValue(file, new TypeReference<List<Map<String, Object>>>() {});
    return covertListToObject(list, clzz);
  }

  public static <T> List<T> deserializationJsonResourceToList(String filePath, Class<T> clzz) {
    try (InputStream inputStream = JacksonUtils.class.getResourceAsStream(filePath)) {
      if (inputStream == null) {
        throw new IOException("Resource not found: " + filePath);
      }
      List<Map<String, Object>> list =
          objectMapper.readValue(inputStream, new TypeReference<List<Map<String, Object>>>() {});
      return covertListToObject(list, clzz);
    } catch (IOException e) {
      log.error("Failed to read the file {}", filePath);
      throw new RuntimeException(e);
    }
  }

  private static <T> List<T> covertListToObject(List<Map<String, Object>> list, Class<T> clzz) {
    List<T> results = new ArrayList<>();
    for (Map<String, Object> item : list) {
      T object = objectMapper.convertValue(item, clzz);
      results.add(object);
    }
    return results;
  }
}
