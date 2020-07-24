package tk.handsome0hell.blog.security;

import tk.handsome0hell.blog.pojo.User;
import java.util.List;

interface UsersRepository {
  Boolean ValidateUser(User user);
  List<User> GetUsers();
  Integer AddUser(User user);
  Integer DeleteUserById(Integer id);
};
