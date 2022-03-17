package study.spring.advanced.trace.callback;

import study.spring.advanced.trace.TraceStatus;
import study.spring.advanced.trace.logtrace.LogTrace;

public class TraceTemplate {

  private final LogTrace trace;

  public TraceTemplate(LogTrace logTrace) {
    this.trace = logTrace;
  }

  public <T> T execute(String message, TraceCallback<T> callback) {
    TraceStatus status = null;
    try {
      status = trace.begin(message);

      // 로직 수행
      T result = callback.call();

      trace.end(status);
      return result;
    } catch (Exception e) {
      trace.exception(status, e);
      throw e;
    }
  }
}
