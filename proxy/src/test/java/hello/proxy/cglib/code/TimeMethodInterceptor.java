package hello.proxy.cglib.code;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

@Slf4j
public class TimeMethodInterceptor implements MethodInterceptor {

  private final Object target;

  public TimeMethodInterceptor(Object target) {
    this.target = target;
  }

  @Override
  public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy)
      throws Throwable {
    log.info("TimeInvocationHandler 시작");
    long startTime = System.currentTimeMillis();

    Object result = methodProxy.invoke(target, args);

    long endTime = System.currentTimeMillis();
    log.info("경과 시간: {}", endTime - startTime);

    return result;
  }
}
