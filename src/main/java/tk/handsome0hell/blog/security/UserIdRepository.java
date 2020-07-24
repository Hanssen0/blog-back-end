package tk.handsome0hell.blog.security;

public interface UserIdRepository {
  void setUserId(Integer id);
  Integer getUserId();
};
