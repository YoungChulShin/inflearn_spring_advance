package hello.proxy.config.v5_autoproxy;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {

//  @Bean
  public Advisor advisor1(LogTrace logTrace) {
    NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
    pointcut.setMappedNames("request*", "order*", "save*");

    LogTraceAdvice logTraceAdvice = new LogTraceAdvice(logTrace);
    return new DefaultPointcutAdvisor(pointcut, logTraceAdvice);
  }

  @Bean
  public Advisor advisor2(LogTrace logTrace) {
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    // * 모든 반환 타입
    // hello.proxy.aop..: 해당 패키지와 하위 패키지
    // *(..): 모든 메서드, 파라미터 상관 없음
    pointcut.setExpression("execution(* hello.proxy.app..*(..))");

    LogTraceAdvice logTraceAdvice = new LogTraceAdvice(logTrace);
    return new DefaultPointcutAdvisor(pointcut, logTraceAdvice);
  }
}
