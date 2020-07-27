package tk.handsome0hell.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.ImportResource;

import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.servlet.function.RequestPredicate;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.RouterFunctions;

import tk.handsome0hell.blog.web.ArticlesPresenter;

import java.util.List;
import java.util.LinkedList;

class Route {
  private RequestPredicate predicate;
  private HandlerFunction<ServerResponse> handler;
  Route(RequestPredicate predicate,
                       HandlerFunction<ServerResponse> handler) {
    this.predicate = predicate;
    this.handler = handler;
  }
  RequestPredicate getPredicate() { return predicate; }
  HandlerFunction<ServerResponse> getHandler() { return handler; }
}

@SpringBootApplication
@ImportResource(value={"classpath:applicationBeans.xml"})
public class BlogApplication {
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
    final List<Route> routes = new LinkedList<Route>();
    routes.add(
      new Route(presenter.getPredicateGetArticles(), presenter::GetArticles)
    );
    routes.add(
      new Route(presenter.getPredicateGetArticle(), presenter::GetArticle)
    );
    routes.add(
      new Route(presenter.getPredicatePutArticle(), presenter::PutArticle)
    );
    RouterFunctions.Builder builder = RouterFunctions.route();
    for (Route route : routes) {
      builder.route(route.getPredicate(), route.getHandler());
    }
    return builder.build();
  }
}
