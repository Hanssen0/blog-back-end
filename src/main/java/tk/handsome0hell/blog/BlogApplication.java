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

import tk.handsome0hell.blog.web.Route;
import tk.handsome0hell.blog.web.ArticlesPresenter;

import java.util.List;

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
    final List<Route> routes = presenter.BuildRoutes();
    RouterFunctions.Builder builder = RouterFunctions.route();
    for (Route route : routes) {
      builder.route(route.getPredicate(), route.getHandler());
    }
    return builder.build();
  }
}
