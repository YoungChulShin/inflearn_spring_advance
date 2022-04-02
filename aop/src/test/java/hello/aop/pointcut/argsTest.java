package hello.aop.pointcut;

import static org.assertj.core.api.Assertions.assertThat;

import hello.aop.member.MemberServiceImpl;
import java.lang.reflect.Method;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

public class argsTest {

  Method helloMethod;

  @BeforeEach
  public void init() throws NoSuchMethodException {
    helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
  }

  private AspectJExpressionPointcut pointcut(String expression) {
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    pointcut.setExpression(expression);
    return pointcut;
  }

  @Test
  void args() {
    assertThat(pointcut("args(String)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
    // args는 상위타입을 허용한다
    assertThat(pointcut("args(Object)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
    assertThat(pointcut("args()").matches(helloMethod, MemberServiceImpl.class)).isFalse();
    // 모든 파라미터
    assertThat(pointcut("args(..)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
    // 파라미터를 1개 가지고 있는 모든 메서드
    assertThat(pointcut("args(*)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
    // String으로 시작하는 모든 메서드
    assertThat(pointcut("args(String, ..)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
    assertThat(pointcut("args(String, *)").matches(helloMethod, MemberServiceImpl.class)).isFalse();
  }

  // execution은 정적으로 타입을 비교 (=타입이 정확히 일치해야한다)
  // args는 동적으로 타입을 비교한다 (=상위타입을 허용한다)
  @Test
  void argsVsExecution() {
    assertThat(pointcut("args(String)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
    assertThat(pointcut("args(java.io.Serializable)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
    assertThat(pointcut("args(Object)").matches(helloMethod, MemberServiceImpl.class)).isTrue();

    assertThat(pointcut("execution(* *(String))").matches(helloMethod, MemberServiceImpl.class)).isTrue();
    assertThat(pointcut("execution(* *(java.io.Serializable))").matches(helloMethod, MemberServiceImpl.class)).isFalse();
    assertThat(pointcut("execution(* *(Object))").matches(helloMethod, MemberServiceImpl.class)).isFalse();
  }
}
