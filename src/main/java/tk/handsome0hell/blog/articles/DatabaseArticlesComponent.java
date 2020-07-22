package tk.handsome0hell.blog.articles;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import tk.handsome0hell.blog.pojo.Article;

class DatabaseArticlesComponent implements ArticlesComponent {
  private ArticlesRepository articles_repository;
  DatabaseArticlesComponent(ArticlesRepository articles_repository) {
    this.articles_repository = articles_repository;
  }
  @Override
  public Article GetArticleById(Integer id) {
    return articles_repository.GetArticleById(id);
  }
  @Override
  public void UpdateArticle(Article article) {
    articles_repository.UpdateArticle(article);
  }
  @Override
  public List<Article> GetArticles() {
    return articles_repository.GetArticles();
  }
};
