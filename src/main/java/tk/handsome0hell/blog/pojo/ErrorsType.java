package tk.handsome0hell.blog.pojo;

public enum ErrorsType {
  kNotLoggedIn("NotLoggedIn"),
  kNoPermission("NoPermission"),
  kAccountNotFound("AccountNotFound");

  private String name;
  private ErrorsType(String name) {
    this.name = name;
  }
  @Override
  public String toString() { return name; }
}

