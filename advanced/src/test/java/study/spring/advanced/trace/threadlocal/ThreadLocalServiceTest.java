package study.spring.advanced.trace.threadlocal;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import study.spring.advanced.trace.threadlocal.code.FieldService;
import study.spring.advanced.trace.threadlocal.code.ThreadLocalService;

@Slf4j
public class ThreadLocalServiceTest {

  private ThreadLocalService service = new ThreadLocalService();

  @Test
  void field() {
    log.info("main start");
    Runnable userA = () -> {
      service.logic("userA");
    };
    Runnable userB = () -> {
      service.logic("userB");
    };

    Thread threadA = new Thread(userA);
    threadA.setName("thread-A");
    Thread threadB = new Thread(userB);
    threadB.setName("thread-B");

    threadA.start();
    sleep(100);
    threadB.start();
    sleep(3000);
  }

  private void sleep(int millis) {
    try {
      Thread.sleep(millis);
    } catch (Exception ex) {

    }
  }
}