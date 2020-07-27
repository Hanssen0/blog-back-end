package tk.handsome0hell.blog.web;

import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.RequestPredicate;
import org.springframework.web.servlet.function.ServerResponse;

import tk.handsome0hell.blog.pojo.PermissionsType;

public class Route {
  private RequestPredicate predicate;
  private HandlerFunction<ServerResponse> handler;
  private PermissionsType permission;
  Route(RequestPredicate predicate,
        HandlerFunction<ServerResponse> handler) {
    this.predicate = predicate;
    this.handler = handler;
  }
  Route AddRequiredPermission(PermissionsType permission) {
    this.permission = permission;
    return this;
  }
  public RequestPredicate getPredicate() { return predicate; }
  public HandlerFunction<ServerResponse> getHandler() { return handler; }
  public PermissionsType getPermission() { return permission; }
}
