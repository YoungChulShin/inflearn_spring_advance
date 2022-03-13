package study.spring.advanced.trace.template;

import study.spring.advanced.trace.TraceStatus;
import study.spring.advanced.trace.logtrace.LogTrace;

public abstract class AbstractTemplate<T> {

  private final LogTrace trace;

  public AbstractTemplate(LogTrace trace) {
    this.trace = trace;
  }

  public T execute(String message) {
    TraceStatus status = null;
    try {
      status = trace.begin(message);

      // 로직 수행
      T result = call();

      trace.end(status);
      return result;
    } catch (Exception e) {
      trace.exception(status, e);
      throw e;
    }
  }

  protected abstract T call();

}
