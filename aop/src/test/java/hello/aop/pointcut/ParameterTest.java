package hello.aop.pointcut;

import hello.aop.member.MemberService;
import hello.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(ParameterTest.ParameterAspect.class)
@SpringBootTest
public class ParameterTest {

  @Autowired
  MemberService memberService;

  @Test
  void success() {
    log.info("memberService Proxy={}", memberService.getClass());
    memberService.hello("helloA");
  }

  @Slf4j
  @Aspect
  static class ParameterAspect {

    @Pointcut("execution(* hello.aop.member..*.*(..))")
    private void allMember() {}

    @Around("allMember()")
    public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
      Object arg1 = joinPoint.getArgs()[0];
      log.info("[logArgs1]{}, args={}", joinPoint.getSignature(), arg1);
      return joinPoint.proceed();
    }

    // args를 적용했기 때문에 Object가 아니라 다른 타입을 하면 pointcut 적용시 타입이 맞아야한다
    @Around("allMember() && args(arg,..)")
    public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
      log.info("[logArgs2]{}, args={}", joinPoint.getSignature(), arg);
      return joinPoint.proceed();
    }

    @Before("allMember() && args(arg,..)")
    public void logArgs3(JoinPoint joinPoint, String arg) {
      log.info("[logArgs3]{}, args={}", joinPoint.getSignature(), arg);
    }

    // [this]String hello.aop.member.MemberServiceImpl.hello(String), obj=class hello.aop.member.MemberServiceImpl$$EnhancerBySpringCGLIB$$4a74700d
    // 컨테이너에 올라가는 대상
    // 프록시
    @Before("allMember() && this(obj)")
    public void thisArgs(JoinPoint joinPoint, MemberService obj) {
      log.info("[this]{}, obj={}", joinPoint.getSignature(), obj.getClass());
    }

    // [target]String hello.aop.member.MemberServiceImpl.hello(String), obj=class hello.aop.member.MemberServiceImpl
    // 프록시가 호출하는 실제 대상
    // 클래스 레벨₩
    @Before("allMember() && target(obj)")
    public void targetArgs(JoinPoint joinPoint, MemberService obj) {
      log.info("[target]{}, obj={}", joinPoint.getSignature(), obj.getClass());
    }

    // annotation을 가져와서 값을 꺼낼 수 있다
    @Before("allMember() && @annotation(annotation)")
    public void atAnnotation(JoinPoint joinPoint, MethodAop annotation) {
      log.info("[@annotation]{}, annotation={}, annotationValue={}", joinPoint.getSignature(), annotation, annotation.value());
    }

    @Before("@annotation(annotation)")
    public void atAnnotationOnly(JoinPoint joinPoint, MethodAop annotation) {
      log.info("[@annotationOnly]{}, annotation={}, annotationValue={}", joinPoint.getSignature(), annotation, annotation.value());
    }
  }
}
