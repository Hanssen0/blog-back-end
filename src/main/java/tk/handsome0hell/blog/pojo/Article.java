package tk.handsome0hell.blog.pojo;

public class Article {
  private String title;
  private String subtitle;
  private String content;
  private String author;
  private Long publish_date;
  public Article() {}
  public Article(String title,
                 String subtitle,
                 String content,
                 String author,
                 Long publish_date) {
    this.title = title;
    this.subtitle = subtitle;
    this.content = content;
    this.author = author;
    this.publish_date = publish_date;
  }
  public String getTitle() {return title;}
  public String getSubtitle() {return subtitle;}
  public String getContent() {return content;}
  public String getAuthor() {return author;}
  public Long getPublish_date() {return publish_date;}
};
