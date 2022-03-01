package study.spring.advanced.trace.logtrace;

import study.spring.advanced.trace.TraceStatus;

public interface LogTrace {

  TraceStatus begin(String message);
  void end(TraceStatus status);
  void exception(TraceStatus status, Exception e);

}
