package tk.handsome0hell.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.ImportResource;

import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.servlet.function.RouterFunctions;

import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.HandlerFunction;

import tk.handsome0hell.blog.web.Route;
import tk.handsome0hell.blog.web.ArticlesPresenter;

import tk.handsome0hell.blog.pojo.PermissionsType;
import tk.handsome0hell.blog.pojo.ResponseBody;

import tk.handsome0hell.blog.permission.PermissionComponent;
import tk.handsome0hell.blog.permission.SessionUserIdRepository;

import java.util.List;

@SpringBootApplication
@ImportResource(value={"classpath:applicationBeans.xml"})
public class BlogApplication {
  private PermissionComponent permission_component;
  BlogApplication(PermissionComponent permission_component) {
    this.permission_component = permission_component;
  }
  public static void main(String[] args) {
    SpringApplication.run(BlogApplication.class, args);
  }
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.
          addMapping("/**").
          allowedOrigins("http://localhost:8080").
          allowCredentials(true).
          allowedMethods("*");
      }
    };
  }
  @Bean
  public RouterFunction<ServerResponse> RouteArticlesPresenter(
      ArticlesPresenter presenter) {
    final List<Route> routes = presenter.BuildRoutes();
    RouterFunctions.Builder builder = RouterFunctions.route();
    for (Route route : routes) {
      HandlerFunction<ServerResponse> decorated_function = route.getHandler();
      decorated_function =
        PermissionDecorator(decorated_function, route.getPermission());
      builder.route(route.getPredicate(), decorated_function);
    }
    return builder.build();
  }
  private HandlerFunction<ServerResponse> PermissionDecorator(
      HandlerFunction<ServerResponse> handler, PermissionsType permission) {
    if (permission == null) return handler;
    return (ServerRequest request) -> {
        ResponseBody response = new ResponseBody();
        if (!response.VerifyPermission(
              permission_component,
              new SessionUserIdRepository(request.session()),
              PermissionsType.kModifyArticle)) {
          return ServerResponse.badRequest().body(response);
        }
        return handler.handle(request);
      };
  }
}
