package tk.handsome0hell.blog.permission;

public interface UserIdRepository {
  void setUserId(Integer id);
  Integer getUserId();
};
