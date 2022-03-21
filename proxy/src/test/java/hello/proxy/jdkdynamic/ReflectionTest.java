package hello.proxy.jdkdynamic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ReflectionTest {

  @Test
  void reflection0() {
    Hello target = new Hello();

    // 공통 로직 1 시작
    log.info("start");
    String result1 = target.callA();
    log.info("result={}", result1);

    // 공통 로직 2 시작
    log.info("start");
    String result2 = target.callB();
    log.info("result={}", result2);
  }

  @Test
  void reflection0_1() {
    Hello target = new Hello();

    // 공통 로직 1 시작
    call(target::callA);

    // 공통 로직 2 시작
    call(target::callB);
  }

  @Test
  void reflection1()
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Class<?> helloClass = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

    Hello hello = new Hello();

    Method callAMethod = helloClass.getMethod("callA");
    Object result1 = callAMethod.invoke(hello);
    log.info("result={}", result1);

    Method callBMethod = helloClass.getMethod("callB");
    Object result2 = callBMethod.invoke(hello);
    log.info("result={}", result2);
  }

  @Test
  void reflection2()
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Class<?> helloClass = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

    Hello hello = new Hello();

    Method callAMethod = helloClass.getMethod("callA");
    dynamicCall(callAMethod, hello);

    Method callBMethod = helloClass.getMethod("callB");
    dynamicCall(callBMethod, hello);
  }

  void dynamicCall(Method method, Object target)
      throws InvocationTargetException, IllegalAccessException {
    log.info("start");
    Object result = method.invoke(target);
    log.info("result={}", result);
  }

  <T> void call(Supplier<T> supplier) {
    log.info("start");
    T result = supplier.get();
    log.info("result={}", result);
  }

  @Slf4j
  static class Hello {
    public String callA() {
      log.info("CallA");
      return "A";
    }

    public String callB() {
      log.info("CallB");
      return "B";
    }
  }

}
