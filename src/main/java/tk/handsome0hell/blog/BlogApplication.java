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
import tk.handsome0hell.blog.web.UsersPresenter;
import tk.handsome0hell.blog.web.LoginPresenter;
import tk.handsome0hell.blog.web.RolesPresenter;

import tk.handsome0hell.blog.pojo.ErrorsType;
import tk.handsome0hell.blog.pojo.PermissionsType;
import tk.handsome0hell.blog.pojo.ResponseBody;

import tk.handsome0hell.blog.permission.PermissionComponent;
import tk.handsome0hell.blog.permission.UserIdRepository;
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
      ArticlesPresenter articles_presenter,
      UsersPresenter users_presenter,
      LoginPresenter login_presenter,
      RolesPresenter roles_presenter) {
    RouterFunctions.Builder builder = RouterFunctions.route();
    AddRoutes(builder, articles_presenter.BuildRoutes());
    AddRoutes(builder, users_presenter.BuildRoutes());
    AddRoutes(builder, login_presenter.BuildRoutes());
    AddRoutes(builder, roles_presenter.BuildRoutes());
    return builder.build();
  }
  private void AddRoutes(
      RouterFunctions.Builder builder, List<Route> routes) {
    for (Route route : routes) {
      HandlerFunction<ServerResponse> decorated_function = route.getHandler();
      decorated_function =
        PermissionDecorator(decorated_function, route.getPermission());
      builder.route(route.getPredicate(), decorated_function);
    }
  }
  private HandlerFunction<ServerResponse> PermissionDecorator(
      HandlerFunction<ServerResponse> handler, PermissionsType permission) {
    if (permission == null) return handler;
    return (ServerRequest request) -> {
        ResponseBody response = new ResponseBody();
        UserIdRepository repository =
          new SessionUserIdRepository(request.session());
        ServerResponse badrequest = ServerResponse.badRequest().body(response);
        if (!permission_component.HasLogined(repository)) {
          response.SetError(ErrorsType.kNotLoggedIn);
        } else if (
            !permission_component.HasPermission(repository, permission)) {
          response.SetError(ErrorsType.kNoPermission, permission.getName());
        } else {
          return handler.handle(request);
        }
        return badrequest;
      };
  }
}
