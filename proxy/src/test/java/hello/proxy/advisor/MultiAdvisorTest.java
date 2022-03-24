package hello.proxy.advisor;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

@Slf4j
public class MultiAdvisorTest {

  @Test
  @DisplayName("여러 프록시")
  void multiAdvisorTest1() {
    // client -> proxy2 -> proxy1 -> target

    // proxy1 생성
    ServiceInterface target = new ServiceImpl();
    ProxyFactory proxyFactory1 = new ProxyFactory(target);
    DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());
    proxyFactory1.addAdvisor(advisor1);
    ServiceInterface proxy1 = (ServiceInterface) proxyFactory1.getProxy();

    // proxy2 생성
    ProxyFactory proxyFactory2 = new ProxyFactory(proxy1);
    DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());
    proxyFactory2.addAdvisor(advisor2);
    ServiceInterface proxy2 = (ServiceInterface) proxyFactory2.getProxy();

    // 실행
    proxy2.save();
  }

  @Test
  @DisplayName("하나의 프록시 여러 어드바이저")
  void multiAdvisorTest2() {
    // client -> proxy2 -> proxy1 -> target

    DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());
    DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(new MyPointcut(), new Advice2());

    // proxy1 생성
    ServiceInterface target = new ServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);
    proxyFactory.addAdvisor(advisor2);
    proxyFactory.addAdvisor(advisor1);
    ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

    // 실행
    proxy.save();
  }

  static class MyPointcut implements Pointcut {

    @Override
    public ClassFilter getClassFilter() {
      return ClassFilter.TRUE;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
      return new MyMethodMatcher();
    }
  }

  @Slf4j
  static class MyMethodMatcher implements MethodMatcher {

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
      log.info("MyMethodMatcher 호출");
      return method.getName().equals("find");
    }

    @Override
    public boolean isRuntime() {
      return false;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass, Object... args) {
      return false;
    }
  }

  @Slf4j
  static class Advice1 implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
      log.info("Advice1 호출");
      return invocation.proceed();
    }
  }

  @Slf4j
  static class Advice2 implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
      log.info("Advice2 호출");
      return invocation.proceed();
    }
  }
}
