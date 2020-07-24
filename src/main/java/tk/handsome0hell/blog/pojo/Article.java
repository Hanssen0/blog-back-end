package tk.handsome0hell.blog.pojo;

public class Article {
  private Integer id;
  private String title;
  private String subtitle;
  private String content;
  private String author;
  private Long publish_time;
  public Article() {}
  public void setId(Integer id) {this.id = id;}
  public void setTitle(String title) {this.title = title;}
  public void setSubtitle(String subtitle) {this.subtitle = subtitle;}
  public void setContent(String content) {this.content = content;}
  public void setAuthor(String author) {this.author = author;}
  public void setPublish_time(Long publish_time) {
    this.publish_time = publish_time;
  }
  public Integer getId() {return id;}
  public String getTitle() {return title;}
  public String getSubtitle() {return subtitle;}
  public String getContent() {return content;}
  public String getAuthor() {return author;}
  public Long getPublish_time() {return publish_time;}
};
