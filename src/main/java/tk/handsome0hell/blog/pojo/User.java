package tk.handsome0hell.blog.pojo;

public class User {
  private Integer id;
  private String username;
  private String password;
  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }
  public void setId(Integer id) {this.id = id;}
  public Integer getId() {return id;}
  public String getUsername() {return this.username;}
  public String getPassword() {return this.password;}
}
