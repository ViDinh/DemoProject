package listener;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.Reporter;

@Slf4j
public class MethodListener implements IInvokedMethodListener {

  @Override
  public void afterInvocation(IInvokedMethod method, ITestResult result) {
    log.debug("After invocation of {}", method.getTestMethod().getMethodName());
    Reporter.setCurrentTestResult(result);

    if (method.isTestMethod()) {
      processTestMethodResult(result);
    }
  }

  private void processTestMethodResult(ITestResult result) {
    ExceptionCollector exceptionCollector = ExceptionCollector.getInstance();
    List<Throwable> allFailures =
        new ArrayList<>(exceptionCollector.getExceptionsOfTestResult(result));

    Throwable testException = result.getThrowable();
    if (testException != null && !(testException instanceof AssertionError)) {
      allFailures.add(testException);
    }

    if (!allFailures.isEmpty()) {
      result.setStatus(ITestResult.FAILURE);
      setThrowableForResult(result, allFailures);
    }
  }

  private void setThrowableForResult(ITestResult result, List<Throwable> failures) {
    if (failures.size() == 1) {
      result.setThrowable(failures.getFirst());
    } else {
      result.setThrowable(createMergedThrowable(failures));
    }
  }

  private Throwable createMergedThrowable(List<Throwable> failures) {
    StringBuilder message =
        new StringBuilder("Multiple failures (").append(failures.size()).append("):\n");

    for (int i = 0; i < failures.size(); i++) {
      message
          .append("Failure ")
          .append(i + 1)
          .append(" of ")
          .append(failures.size())
          .append(" - ")
          .append(failures.get(i).getClass().getSimpleName())
          .append(": ")
          .append(failures.get(i).getMessage())
          .append("\n");
      for (StackTraceElement element : failures.get(i).getStackTrace()) {
        message.append("    at ").append(element).append("\n");
      }
      message.append("\n");
    }

    return new Throwable(message.toString()) {
      @Override
      public synchronized Throwable fillInStackTrace() {
        return this;
      }
    };
  }
}
