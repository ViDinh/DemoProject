package allure;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AllureAppender extends AppenderBase<ILoggingEvent> {
  private static final AllureLifecycle lifeCycle = Allure.getLifecycle();
  private static final ThreadLocal<StringBuilder> logs = new ThreadLocal<>();
  private List<String> includedPackages = new ArrayList<>();

  public void setIncludedPackages(String packages) {
    this.includedPackages = Arrays.asList(packages.split(","));
  }

  @Override
  protected void append(ILoggingEvent loggingEvent) {
    String message = loggingEvent.getFormattedMessage();
    StringBuilder builder = logs.get();
    String uuid = "";
    StepResult result;
    if (builder == null) {
      builder = new StringBuilder();
      logs.set(builder);
    }

    if (loggingEvent.getLevel().toString().equals("INFO")
        && isPackedIncluded(loggingEvent.getLoggerName())) {
      builder.append(message).append("\n");
      uuid = UUID.randomUUID().toString();
      result = new StepResult().setName("Log").setStatus(Status.PASSED);
      lifeCycle.startStep(uuid, result);
    } else if (loggingEvent.getLevel().toString().equals("ERROR")
        && isPackedIncluded(loggingEvent.getLoggerName())) {
      builder.append(message).append("\n");
      uuid = UUID.randomUUID().toString();
      result = new StepResult().setName("Log").setStatus(Status.FAILED);
      lifeCycle.startStep(uuid, result);
    }

    lifeCycle.updateStep(uuid, step -> step.setName(message));
    lifeCycle.stopStep(uuid);
  }

  private boolean isPackedIncluded(String loggerName) {
    if (includedPackages.isEmpty()) {
      return true;
    }
    for (String pkg : includedPackages) {
      if (loggerName.startsWith(pkg.trim())) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void stop() {
    StringBuilder builder = logs.get();
    if (builder != null && !builder.isEmpty()) {
      Allure.addAttachment("Consolidated INFO logs", "text/plain", builder.toString());
      logs.remove();
    }
    super.stop();
  }
}
