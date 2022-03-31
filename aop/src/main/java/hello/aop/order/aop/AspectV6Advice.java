package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Slf4j
@Aspect
public class AspectV6Advice {

//  @Around("hello.aop.order.aop.PointCuts.orderAndAllService()")
//  public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
//    try {
//      log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
//      Object result = joinPoint.proceed();
//      log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
//      return result;
//    } catch (Exception ex) {
//      log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
//      throw ex;
//    } finally {
//      log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
//    }
//  }

  @Before("hello.aop.order.aop.PointCuts.orderAndAllService()")
  public void doBefore(JoinPoint joinPoint) {
    log.info("[before] {}", joinPoint.getSignature());
  }

  @AfterReturning(value = "hello.aop.order.aop.PointCuts.orderAndAllService()", returning = "result")
  public void doReturn(JoinPoint joinPoint, Object result) {
    // 이미 result는 정해진 값이기 때문에 변경은 어렵다
    log.info("[return] {} return={}", joinPoint.getSignature(), result);
  }
}
