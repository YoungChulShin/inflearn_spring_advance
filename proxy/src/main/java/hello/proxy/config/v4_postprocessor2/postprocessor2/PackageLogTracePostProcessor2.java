package hello.proxy.config.v4_postprocessor2.postprocessor2;

import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

@Slf4j
public class PackageLogTracePostProcessor2 implements BeanPostProcessor {

  private final String basePackage;
  private final Advisor advisor;

  public PackageLogTracePostProcessor2(String basePackage, Advisor advisor) {
    this.basePackage = basePackage;
    this.advisor = advisor;
  }

  // 빈 후처리기
  // 스프링 컨테이너에 빈을 등록하기전에
  // 빈을 프록시로 변경해주는 작업을 한다
  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    String packageName = bean.getClass().getPackageName();
    if (!packageName.startsWith(basePackage)) {
      return bean;
    }

    ProxyFactory proxyFactory = new ProxyFactory(bean);
    proxyFactory.addAdvisor(advisor);

    Object proxy = proxyFactory.getProxy();
    return proxy;
  }
}
