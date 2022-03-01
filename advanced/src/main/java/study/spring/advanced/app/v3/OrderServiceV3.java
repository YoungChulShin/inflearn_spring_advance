package study.spring.advanced.app.v3;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.spring.advanced.trace.TraceId;
import study.spring.advanced.trace.TraceStatus;
import study.spring.advanced.trace.hellotrace.HelloTraceV2;
import study.spring.advanced.trace.logtrace.LogTrace;

@Service
@RequiredArgsConstructor
public class OrderServiceV3 {

  private final OrderRepositoryV3 orderRepository;
  private final LogTrace trace;

  public void orderItem(String itemId) {

    TraceStatus status = null;
    try {
      status = trace.begin("OrderService.request");
      orderRepository.save(itemId);
      trace.end(status);
    } catch (Exception e) {
      trace.exception(status, e);
      throw e;
    }
  }

}
