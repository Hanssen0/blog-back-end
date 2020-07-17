package tk.handsome0hell.blog.web;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import tk.handsome0hell.blog.pojo.Article;
import tk.handsome0hell.blog.articles.ArticlesComponent;

@RestController
@RequestMapping("/articles")
public class ArticlesController {
  @Autowired
  private ArticlesComponent articles_component;
  @GetMapping("")
  public List<Article> GetArticles() {
    return articles_component.GetArticles();
  }
  @GetMapping("{id}")
  public Article GetArticle(@PathVariable("id") Integer id) {
    return articles_component.GetArticleById(id);
  }
  @PutMapping("{id}")
  public void PutArticle(@PathVariable("id") Integer id, @RequestBody Article article) {
    article.setId(id);
    articles_component.UpdateArticle(article);
  }
}
