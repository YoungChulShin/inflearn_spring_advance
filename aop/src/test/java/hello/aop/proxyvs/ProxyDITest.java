package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import hello.aop.proxyvs.code.ProxyDIAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
// 이렇게 테스트에서 설정을 적용할 수 있다
@SpringBootTest(properties = {
    // jdk 동적 프록시. interface가 없으면 cglib 사용
    // 스프링 기본은 cglib
    "spring.aop.proxy-target-class=false"
})
@Import(ProxyDIAspect.class)
public class ProxyDITest {

  // jdk proxy는 interface 기반이므로 MemberService로 캐스팅할 수 있다
  @Autowired
  MemberService memberService;

  // jdk proxy는 interface 기반이므로 구체 클래스로 캐스팅할 수 없다
  @Autowired
  MemberServiceImpl memberServiceImpl;

  @Test
  void go() {
    log.info("memberService class={}", memberService.getClass());
    log.info("memberServiceImpl class={}", memberServiceImpl.getClass());

    memberServiceImpl.hello("hello");
  }

}
