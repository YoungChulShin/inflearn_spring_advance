package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageDecorator implements Component {

  private final Component realComponent;

  public MessageDecorator(Component realComponent) {
    this.realComponent = realComponent;
  }

  @Override
  public String operation() {
    log.info("MessageDecorator 실행");
    String result = realComponent.operation();
    String decoratedResult = "****" + result + "****";
    log.info("MessageDecorator 꾸미기 적용 전={}, 적용 후={}", result, decoratedResult);
    return decoratedResult;
  }
}
