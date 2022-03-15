package study.spring.advanced.trace.strategy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import study.spring.advanced.trace.strategy.code.strategy.ContextV1;
import study.spring.advanced.trace.strategy.code.strategy.ContextV2;
import study.spring.advanced.trace.strategy.code.strategy.Strategy;
import study.spring.advanced.trace.strategy.code.strategy.StrategyLogic1;
import study.spring.advanced.trace.strategy.code.strategy.StrategyLogic2;

@Slf4j
public class ContextV2Test {

  @Test
  void strategyV1() {
    ContextV2 context = new ContextV2();
    context.execute(new StrategyLogic1());
    context.execute(new StrategyLogic2());
  }

  @Test
  void strategyV2() {
    ContextV2 context = new ContextV2();
    context.execute(new Strategy() {
      @Override
      public void call() {
        log.info("비지니스 로직 실행");
      }
    });
  }

  @Test
  void strategyV3() {
    ContextV2 context = new ContextV2();
    context.execute(() -> log.info("비지니스 로직 실행"));
  }
}
