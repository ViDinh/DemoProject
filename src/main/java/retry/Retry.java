package retry;

import lombok.extern.slf4j.Slf4j;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

@Slf4j
public class Retry implements IRetryAnalyzer {
  private static final int MAX_RETRY = 1;
  private int count = 0;

  @Override
  public boolean retry(final ITestResult iTestResult) {
    if (!iTestResult.isSuccess()) {
      if (this.count < MAX_RETRY) {
        log.info("Retrying test {} for the {} time(s).", iTestResult.getName(), this.count + 1);
        this.count++;
        return true;
      }
    }
    return false;
  }
}
