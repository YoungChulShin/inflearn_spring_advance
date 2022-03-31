package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;


@Slf4j
@Aspect // boot-starter-aop를 추가하면 boot가 자동으로 @EnableAspectJAutoProxy를 해준다
        // apsect 애노테이션이 선언되어 있으면 pointcut과 advice로 등록한다
public class AspectV1 {

  @Around("execution(* hello.aop.order..*(..))")  // 여기가 포인트컷
  public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
    // 여기거 어드바이스
    log.info("[log] {}", joinPoint.getSignature());
    return joinPoint.proceed();
  }

}
