package utils;

import constants.Constants;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

@Slf4j
public class FileUtils {

  private FileUtils() {}

  public static void clearDirectory(String folderName) {
    Path pathToBeDeleted = Paths.get(Constants.ROOT_PATH + folderName);
    try (Stream<Path> pathStream = Files.walk(pathToBeDeleted)) {
      pathStream.sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
    } catch (IOException e) {
      log.error("Failed to delete file in {}", folderName);
    }
  }
}
