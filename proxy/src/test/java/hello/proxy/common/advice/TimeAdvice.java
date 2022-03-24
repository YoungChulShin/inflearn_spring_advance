package hello.proxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TimeAdvice implements MethodInterceptor {

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    log.info("TimeInvocationHandler 시작");
    long startTime = System.currentTimeMillis();

    // target을 찾아서 메서드를 실행해준다
    Object result = invocation.proceed();

    long endTime = System.currentTimeMillis();
    log.info("경과 시간: {}", endTime - startTime);

    return result;
  }
}
