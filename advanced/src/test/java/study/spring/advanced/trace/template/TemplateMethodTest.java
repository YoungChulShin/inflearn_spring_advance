package study.spring.advanced.trace.template;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import study.spring.advanced.trace.template.code.AbstractTemplate;
import study.spring.advanced.trace.template.code.SubClassLogic1;
import study.spring.advanced.trace.template.code.SubClassLogic2;

@Slf4j
public class TemplateMethodTest {

  @Test
  void templateMethodV0() {
    logic1();
    logic2();
  }

  private void logic1() {
     long startTime = System.currentTimeMillis();

     log.info("비지니스 로직1 실행");

     long endTime = System.currentTimeMillis();
     long resultTime = endTime - startTime;
     log.info("resultTime={}", resultTime);
  }

  private void logic2() {
    long startTime = System.currentTimeMillis();

    log.info("비지니스 로직2 실행");

    long endTime = System.currentTimeMillis();
    long resultTime = endTime - startTime;
    log.info("resultTime={}", resultTime);
  }

  @Test
  void templateMethodV1() {
    AbstractTemplate abstractTemplate = new SubClassLogic1();
    abstractTemplate.execute();
  }

  @Test
  void templateMethodV2() {
    AbstractTemplate abstractTemplate = new SubClassLogic2();
    abstractTemplate.execute();
  }

  @Test
  void templateMethodV3() {
    AbstractTemplate abstractTemplate = new AbstractTemplate() {
      @Override
      protected void call() {
        log.info("비지늣 로직 3실행");
      }
    };
    // 클래스를 임의로 만들어 준다. $를 붙여준다.
    log.info("클래스 이름 = {}", abstractTemplate.getClass());
    abstractTemplate.execute();
  }
}
