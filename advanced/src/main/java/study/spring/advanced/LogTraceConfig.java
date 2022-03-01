package study.spring.advanced;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.spring.advanced.trace.logtrace.FieldLogTrace;
import study.spring.advanced.trace.logtrace.LogTrace;

@Configuration
public class LogTraceConfig {

  @Bean
  public LogTrace logTrace() {
    return new FieldLogTrace();
  }

}
