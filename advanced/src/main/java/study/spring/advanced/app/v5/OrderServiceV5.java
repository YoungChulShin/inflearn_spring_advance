package study.spring.advanced.app.v5;

import org.springframework.stereotype.Service;
import study.spring.advanced.trace.callback.TraceTemplate;
import study.spring.advanced.trace.logtrace.LogTrace;

@Service
public class OrderServiceV5 {

  private final OrderRepositoryV5 orderRepository;
  private final TraceTemplate template;

  public OrderServiceV5(
      OrderRepositoryV5 orderRepository,
      LogTrace logTrace) {
    this.orderRepository = orderRepository;
    this.template = new TraceTemplate(logTrace);
  }

  public void orderItem(String itemId) {
    template.execute(
        "OrderService.request",
        () -> {
          orderRepository.save(itemId);
          return null;
        }
    );
  }
}
