package tk.handsome0hell.blog.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tk.handsome0hell.blog.pojo.ResponseBody;
import tk.handsome0hell.blog.pojo.Article;
import tk.handsome0hell.blog.articles.ArticlesComponent;
import tk.handsome0hell.blog.pojo.PermissionsType;
import tk.handsome0hell.blog.permission.SessionUserIdRepository;
import tk.handsome0hell.blog.permission.PermissionComponent;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/articles")
public class ArticlesPresenter {
  private ArticlesComponent articles_component;
  private PermissionComponent permission_component;
  public ArticlesPresenter(
      ArticlesComponent articles_component,
      PermissionComponent permission_component) {
    this.articles_component = articles_component;
    this.permission_component = permission_component;
  }
  @GetMapping("")
  public ResponseBody GetArticles() {
    return new ResponseBody(articles_component.GetArticles());
  }
  @GetMapping("{id}")
  public ResponseBody GetArticle(@PathVariable("id") Integer id) {
    // TODO: Handle article not fund error
    return new ResponseBody(articles_component.GetArticleById(id));
  }
  @PutMapping("{id}")
  public ResponseBody PutArticle(
      @PathVariable("id") Integer id,
      @RequestBody Article article,
      HttpSession session) {
    ResponseBody response = new ResponseBody();
    if (!response.VerifyPermission(permission_component,
                                   new SessionUserIdRepository(session),
                                   PermissionsType.kModifyArticle)) {
      return response;
    }
    article.setId(id);
    articles_component.UpdateArticle(article);
    return response;
  }
}
