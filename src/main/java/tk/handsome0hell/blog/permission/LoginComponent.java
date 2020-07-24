package tk.handsome0hell.blog.permission;

import tk.handsome0hell.blog.pojo.User;

public interface LoginComponent {
  void Login(UserIdRepository repository, User user);
  void Logout(UserIdRepository repository);
};
