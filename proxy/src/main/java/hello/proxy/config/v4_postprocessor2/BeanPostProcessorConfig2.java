package hello.proxy.config.v4_postprocessor2;

import hello.proxy.config.v4_postprocessor2.postprocessor2.PackageLogTracePostProcessor2;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;

public class BeanPostProcessorConfig2 {

  @Bean
  public PackageLogTracePostProcessor2 packageLogTracePostProcessor2(LogTrace logTrace) {
    return new PackageLogTracePostProcessor2(
        "hello.proxy.aop",
        getAdvisor(logTrace));
  }

  private Advisor getAdvisor(LogTrace logTrace) {
    NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
    pointcut.setMappedNames("request*", "order*", "save*");

    MyLogTraceAdvice myLogTraceAdvice = new MyLogTraceAdvice(logTrace);

    return new DefaultPointcutAdvisor(
        pointcut,
        myLogTraceAdvice);
  }

  @Slf4j
  static class MyLogTraceAdvice implements MethodInterceptor {

    private final LogTrace logTrace;

    public MyLogTraceAdvice(LogTrace logTrace) {
      this.logTrace = logTrace;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
      log.info("invoke start");

      Object result = invocation.proceed();

      log.info("invoke end");
      return result;
    }
  }
}
