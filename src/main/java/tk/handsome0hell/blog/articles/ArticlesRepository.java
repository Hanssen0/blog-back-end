package tk.handsome0hell.blog.articles;

import java.util.List;
import tk.handsome0hell.blog.pojo.Article;

interface ArticlesRepository {
  Article GetArticleById(Integer id);
  void UpdateArticle(Article article);
  List<Article> GetArticles();
};