package driver.web;

import java.util.Arrays;
import java.util.List;

public enum Browser {
  CHROME("gc", "chrome", "Chrome"),
  FIREFOX("ff", "firefox", "Firefox"),
  REMOTE("rm", "remote"),
  HEADLESS_CHROME("hc", "headless_chrome");

  final List<String> browserNames;

  Browser(String... browserNames) {
    this.browserNames = Arrays.asList(browserNames);
  }

  public static Browser fromValues(String input) {
    for (Browser browser : Browser.values()) {
      if (browser.browserNames.contains(input)) {
        return browser;
      }
    }
    return CHROME;
  }
}
