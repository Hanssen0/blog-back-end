package tk.handsome0hell.blog.web;

import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.RequestPredicate;
import org.springframework.web.servlet.function.ServerResponse;

public class Route {
  private RequestPredicate predicate;
  private HandlerFunction<ServerResponse> handler;
  Route(RequestPredicate predicate,
        HandlerFunction<ServerResponse> handler) {
    this.predicate = predicate;
    this.handler = handler;
  }
  public RequestPredicate getPredicate() { return predicate; }
  public HandlerFunction<ServerResponse> getHandler() { return handler; }
}
