package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DocoratorPatternClient {

  private Component component;

  public DocoratorPatternClient(Component component) {
    this.component = component;
  }

  public void execute() {
    String result = component.operation();
    log.info("result = {}", result);
  }
}
