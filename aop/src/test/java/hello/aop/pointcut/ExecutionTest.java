package hello.aop.pointcut;

import static org.assertj.core.api.Assertions.assertThat;

import hello.aop.member.MemberServiceImpl;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

@Slf4j
public class ExecutionTest {

  // execution 표현식
  // execution (접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
  // ?는 생략 가능
  // *같은 패턴을 적용 가능
  AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
  Method helloMethod;

  @BeforeEach
  public void init() throws NoSuchMethodException {
    helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
  }

  @Test
  void pintMethod() {
    // helloMethod=public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
    log.info("helloMethod={}", helloMethod);
  }

  @Test
  void exactMath() {
    // helloMethod=public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
    pointcut.setExpression("execution(public String hello.aop.member.MemberServiceImpl.hello(String))");
    // 접근제어자?: public
    // 반환타입: String
    // 선언타입?: 패키지 + 클래스명 : hello.aop.member.MemberServiceImpl
    // 메서드명: hello
    // 파라미터: String
    // 예외?: 생략
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  void allMatch() {
    // helloMethod=public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
    pointcut.setExpression("execution(* *(..))");
    // 접근제어자?: 생략
    // 반환타입: all
    // 선언타입?: 생략
    // 메서드명: all
    // 파라미터: all. (..)은 파라미터 타입과 파라미터 수가 상관없다는 뜻
    // 예외?: 생략
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  void nameMatch() {
    // helloMethod=public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
    pointcut.setExpression("execution(* hello(..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  void nameMatchStar1() {
    // helloMethod=public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
    pointcut.setExpression("execution(* hel*(..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  void nameMatchStar2() {
    // helloMethod=public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
    pointcut.setExpression("execution(* *el*(..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  void nameMatchFalse() {
    // helloMethod=public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
    pointcut.setExpression("execution(* nono(..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
  }

  @Test
  void packageExactMatch1() {
    // helloMethod=public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
    pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.hello(..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  void packageExactMatch2() {
    // helloMethod=public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
    pointcut.setExpression("execution(* hello.aop.member.*.hello(..))");
    // hello.aop.member 패키지의 모든 클래스 중에 hello 메서드를 처리
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  void packageExactMatch2_1() {
    // helloMethod=public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
    pointcut.setExpression("execution(* hello.aop.member.*.*(..))");
    // hello.aop.member 패키지의 모든 클래스, 모든 메서드를 처리
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  void packageExactFalse() {
    // helloMethod=public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
    pointcut.setExpression("execution(* hello.aop.*.*(..))");
    // hello.aop 패키지에 모든 클래스/메서드 중에서 처리
    // MemberServiceImpl은 hello.aop.member 패키지 하위에 있기 때문에 실패하게 됨
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
  }

  @Test
  void packageMatchSubPackage() {
    // helloMethod=public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
    pointcut.setExpression("execution(* hello.aop.member..*.*(..))");
    // hello.aop.member 및 하위의 모든 패키지에 대응
    // .은 정확한 패키지
    // ..은 해당 패키지 및 하위 패키지를 포함
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  void packageMatchSubPackage2() {
    // helloMethod=public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
    pointcut.setExpression("execution(* hello.aop..*.*(..))");
    // hello.aop.member 및 하위의 모든 패키지에 대응
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  void typeExactMach() {
    // helloMethod=public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
    pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  void typeMatchSuperType() {
    // helloMethod=public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
    pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
    // 부모타입에 지정해도 동작을 한다
    // 타입 매칭
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  void typeMatchInternal() throws NoSuchMethodException {
    // helloMethod=public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
    pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
    // 부모 타입에 있는 메서드에 대해서만 적용된다
    Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
    assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isFalse();
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  // String 타입 파라미터를 허용
  @Test
  void argsMatch() {
    pointcut.setExpression("execution(* *(String))");
    // String을 파라미터로 가지는 모든 메서드
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  void argsMatchNoArgs() {
    pointcut.setExpression("execution(* *())");
    // 파라미터가 없어야하는데 String을 가지고 있기 때문에 실패
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
  }

  @Test
  void argsMatchAll() {
    pointcut.setExpression("execution(* *(..))");
    // 모든파라미터, 타입을 허용
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  void argsMatchComplex() {
    pointcut.setExpression("execution(* *(String, ..))");
    // 파라미터가 String으로 시작
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }
}
