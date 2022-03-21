package hello.proxy.jdkdynamic.code;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeInvocationHandler implements InvocationHandler {

  private final Object target;

  public TimeInvocationHandler(Object target) {
    this.target = target;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    log.info("TimeInvocationHandler 시작");
    long startTime = System.currentTimeMillis();

    // method는 call이 넘어온다
    // 여기는 reflection
    Object result = method.invoke(target, args);

    long endTime = System.currentTimeMillis();
    log.info("경과 시간: {}", endTime - startTime);

    return result;
  }
}
