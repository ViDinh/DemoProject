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

  public void setIncludedPackages(String packages){
    this.includedPackages = Arrays.asList(packages.split(","));
  }

  @Override
  protected void append(ILoggingEvent iLoggingEvent) {
    if (iLoggingEvent.getLevel().toString().equals("INFO") && isPackedIncluded(iLoggingEvent.getLoggerName())){
      String message = iLoggingEvent.getFormattedMessage();
      StringBuilder builder = logs.get();
      if (builder == null){
        builder = new StringBuilder();
        logs.set(builder);
      }
      builder.append(message).append("\n");

      String uuid = UUID.randomUUID().toString();
      StepResult result = new StepResult().setName("Log").setStatus(Status.PASSED);
      lifeCycle.startStep(uuid, result);
      lifeCycle.updateStep(uuid, step -> step.setName(message));
      lifeCycle.stopStep(uuid);
    }
  }

  private boolean isPackedIncluded(String loggerName  ){
    if (includedPackages.isEmpty()){
      return true;
    }
    for (String pkg: includedPackages){
      if (loggerName.startsWith(pkg.trim())){
        return true;
      }
    }
    return false;
  }

  @Override
  public void stop(){
    StringBuilder builder = logs.get();
    if (builder!=null && !builder.isEmpty()){
      Allure.addAttachment("Consolidated INFO logs", "text/plain", builder.toString());
      logs.remove();
    }
    super.stop();
  }
}
