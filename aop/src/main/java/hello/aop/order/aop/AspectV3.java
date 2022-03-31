package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


@Slf4j
@Aspect
public class AspectV3 {

  // 다른 aspect에서 참고하려면 public으로 해도 된다
  // 하나의 포인트컷을 여러 어드바이스에서 사용할 수 있다
  @Pointcut("execution(* hello.aop.order..*(..))")
  private void allOrder(){} // pointcut signature

  // 클래스 이름 패턴이 *Service인것
  @Pointcut("execution(* *..*Service.*(..))")
  private void allService(){}


  @Around("allOrder()")
  public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
    // 여기거 어드바이스
    log.info("[log] {}", joinPoint.getSignature());
    return joinPoint.proceed();
  }

  @Around("allOrder() && allService()")
  public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
    try {
      log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
      Object result = joinPoint.proceed();
      log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
      return result;
    } catch (Exception ex) {
      log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
      throw ex;
    } finally {
      log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
    }
  }
}
