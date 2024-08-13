package listener;

import org.testng.ITestResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExceptionCollector extends HashMap<ITestResult, List<Throwable>> {
  private static ExceptionCollector collectExceptions;

  private ExceptionCollector() {}

  public static ExceptionCollector getInstance() {
    if (collectExceptions == null) {
      collectExceptions = new ExceptionCollector();
    }
    return collectExceptions;
  }

  public static void resetInstance() {
    collectExceptions = null;
  }

  public List<Throwable> getExceptionsOfTestResult(ITestResult iTestResult) {
    List<Throwable> exceptions = this.get(iTestResult);
    return exceptions == null ? new ArrayList<>() : exceptions;
  }

  public void addExceptionToTestResult(ITestResult iTestResult, Throwable throwable) {
    List<Throwable> exceptions = this.getExceptionsOfTestResult(iTestResult);
    exceptions.add(throwable);
    this.put(iTestResult, exceptions);
  }
}
