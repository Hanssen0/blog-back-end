package tk.handsome0hell.blog.pojo;

public class Article {
  private Integer id;
  private String title;
  private String subtitle;
  private String content;
  private String author;
  private Long publish_time;
  public Article() {}
  public Article(String title,
                 String subtitle,
                 String content,
                 String author,
                 Long publish_time) {
    this.title = title;
    this.subtitle = subtitle;
    this.content = content;
    this.author = author;
    this.publish_time = publish_time;
  }
  public void CopyFrom(Article article) {
    if (article.title != null) title = article.title;
    if (article.subtitle != null) subtitle = article.subtitle;
    if (article.content != null) content = article.content;
    if (article.author != null) author = article.author;
    if (article.publish_time != null) publish_time = article.publish_time;
  }
  public void setId(Integer id) {this.id = id;}
  public Integer getId() {return id;}
  public String getTitle() {return title;}
  public String getSubtitle() {return subtitle;}
  public String getContent() {return content;}
  public String getAuthor() {return author;}
  public Long getPublish_time() {return publish_time;}
};
