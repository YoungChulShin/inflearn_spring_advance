package hello.proxy.jdkdynamic;

import java.util.function.Consumer;
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
