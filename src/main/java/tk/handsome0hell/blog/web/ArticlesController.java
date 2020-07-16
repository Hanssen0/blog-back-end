package tk.handsome0hell.blog.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import tk.handsome0hell.blog.pojo.Article;
import tk.handsome0hell.blog.articles.ArticlesComponent;

@RestController
public class ArticlesController {
  @Autowired
  private ArticlesComponent articles_component;
	@GetMapping("/article")
	public Article GetArticle() {
    return articles_component.GetArticleById(0);
	}
}
