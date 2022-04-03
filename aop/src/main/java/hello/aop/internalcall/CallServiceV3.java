package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV3 {

  private final InternalService internal;

  public CallServiceV3(InternalService internal) {
    this.internal = internal;
  }

  public void external() {
    log.info("call external");
    internal.internal();
  }


}
