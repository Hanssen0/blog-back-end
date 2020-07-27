package tk.handsome0hell.blog.web;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.RequestPredicate;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.servlet.function.ServerRequest;

import tk.handsome0hell.blog.pojo.ResponseBody;
import tk.handsome0hell.blog.pojo.Article;
import tk.handsome0hell.blog.articles.ArticlesComponent;
import tk.handsome0hell.blog.pojo.PermissionsType;

import javax.servlet.ServletException;
import java.io.IOException;

import java.util.List;
import java.util.LinkedList;

@Component
public class ArticlesPresenter {
  private ArticlesComponent articles_component;
  public ArticlesPresenter(ArticlesComponent articles_component) {
    this.articles_component = articles_component;
  }
  public List<Route> BuildRoutes() {
    final String root = "/articles";
    final List<Route> routes = new LinkedList<Route>();
    routes.add(new Route(RequestPredicates.GET(root + ""), this::GetArticles));
    routes.add(
      new Route(RequestPredicates.GET(root + "/{id}"), this::GetArticle));
    routes.add(
      new Route(RequestPredicates.PUT(root + "/{id}"), this::PutArticle)
        .AddRequiredPermission(PermissionsType.kModifyArticle)
    );
    return routes;
  }
  ServerResponse GetArticles(ServerRequest request) {
    return ServerResponse.ok()
             .body(new ResponseBody(articles_component.GetArticles()));
  }
  ServerResponse GetArticle(ServerRequest request) {
    // TODO: Handle number parsing error
    Integer id = Integer.parseInt(request.pathVariable("id"));
    // TODO: Handle article not fund error
    return ServerResponse.ok()
             .body(new ResponseBody(articles_component.GetArticleById(id)));
  }
  ServerResponse PutArticle(ServerRequest request) {
    Article article;
    // TODO: Handle wrong entity errors
    try { article = request.body(Article.class); }
    catch (ServletException expection) {
      return ServerResponse.unprocessableEntity().build();
    } catch (IOException expection) {
      return ServerResponse.unprocessableEntity().build();
    }
    // TODO: Handle number parsing error
    Integer id = Integer.parseInt(request.pathVariable("id"));
    article.setId(id);
    articles_component.UpdateArticle(article);
    return ServerResponse.ok().body(new ResponseBody());
  }
}
