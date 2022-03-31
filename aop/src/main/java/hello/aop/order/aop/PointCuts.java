package hello.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {

  // 다른 aspect에서 참고하려면 public으로 해도 된다
  // 하나의 포인트컷을 여러 어드바이스에서 사용할 수 있다
  @Pointcut("execution(* hello.aop.order..*(..))")
  public void allOrder(){} // pointcut signature

  // 클래스 이름 패턴이 *Service인것
  @Pointcut("execution(* *..*Service.*(..))")
  public void allService(){}

  @Pointcut("allOrder() && allService()")
  public void orderAndAllService() {}

}
