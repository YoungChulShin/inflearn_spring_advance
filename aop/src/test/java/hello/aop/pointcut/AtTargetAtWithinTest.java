package hello.aop.pointcut;

import hello.aop.member.annotation.ClassAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Slf4j
@Import({AtTargetAtWithinTest.Config.class})
@SpringBootTest
public class AtTargetAtWithinTest {

  @Autowired
  Child child;

  // child Proxy=class hello.aop.pointcut.AtTargetAtWithinTest$Child$$EnhancerBySpringCGLIB$$765bbdf
  // [@target] void hello.aop.pointcut.AtTargetAtWithinTest$Child.childMethod()
  // [@within] void hello.aop.pointcut.AtTargetAtWithinTest$Child.childMethod()
  // [@target] void hello.aop.pointcut.AtTargetAtWithinTest$Parent.parentMethod()
  @Test
  void success() {
    log.info("child Proxy={}", child.getClass());
    child.childMethod();
    child.parentMethod();
  }


  static class Config {
    @Bean
    public Parent child() {
      return new Child();
    }

    @Bean
    public Parent parent() {
      return new Parent();
    }

    @Bean
    public AtTargetAtWithinAspect atTargetAtWithinAspect() {
      return new AtTargetAtWithinAspect();
    }
  }

  static class Parent {
    public void parentMethod() {}
  }

  @ClassAop
  static class Child extends Parent {
    public void childMethod() {}
  }

  // @target, @within, args 등은 execution 없이 사용하면 안된다.
  // execution으로 대상을 줄여야한다
  @Slf4j
  @Aspect
  static class AtTargetAtWithinAspect {

    // 인스턴스 기준으로 모든 메서드에 조인 포인트를 설정
    // 부모 클래스 포함
    @Around("execution(* hello.aop..*(..)) && @target(hello.aop.member.annotation.ClassAop)")
    public Object atTarget(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
      log.info("[@target] {}", proceedingJoinPoint.getSignature());
      return proceedingJoinPoint.proceed();
    }

    // 선택된 클래스의 내부에 있는 메서드만 조인 포인트로 설정
    // within으로 하면 하위타입이 포함되는것 같은데..?
    @Around("execution(* hello.aop..*(..)) && @within(hello.aop.member.annotation.ClassAop)")
    public Object atWithin(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
      log.info("[@within] {}", proceedingJoinPoint.getSignature());
      return proceedingJoinPoint.proceed();
    }
  }

}
