package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

@Slf4j
public class ProxyCastingTest {

  @Test
  void jdkProxy() {
    MemberServiceImpl target = new MemberServiceImpl();

    ProxyFactory proxyFactory = new ProxyFactory(target);
    proxyFactory.setProxyTargetClass(false);  // jdk 동적 프록시

    // 프록시를 인터페이스로 캐스팅
    MemberService memberServiceProxy = (MemberService)proxyFactory.getProxy();

    // jdk 동적 프록시를 구현클래스로 캐스팅하면 실패한다
    // jdk 동적 프록시는 interface를 기반으로 프록시를 만들기 때문에 그렇다
    Assertions.assertThrows(
        ClassCastException.class,
        () -> {
          MemberServiceImpl castingMemberServiceProxy = (MemberServiceImpl)proxyFactory.getProxy();
        });
  }

  @Test
  void cglibProxy() {
    MemberServiceImpl target = new MemberServiceImpl();

    ProxyFactory proxyFactory = new ProxyFactory(target);
    proxyFactory.setProxyTargetClass(true);  // CGLIB 프록시

    // 프록시를 인터페이스로 캐스팅
    MemberService memberServiceProxy = (MemberService)proxyFactory.getProxy();

    // CGLIB 프록시를 구현클래스로 캐스팅할 수 있다
    MemberServiceImpl castingMemberServiceProxy = (MemberServiceImpl)proxyFactory.getProxy();
  }
}
