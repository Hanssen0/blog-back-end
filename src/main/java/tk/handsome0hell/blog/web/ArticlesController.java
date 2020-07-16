package tk.handsome0hell.blog.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

class Article {
  private String title;
  private String content;
  public Article(String title, String content) {
    this.title = title;
    this.content = content;
  }
  public String getTitle() {return title;}
  public String getContent() {return content;}
};

@RestController
public class ArticlesController {
	@GetMapping("/article")
	public Article GetArticle() {
    return new Article("Title", "Content");
	}
}
