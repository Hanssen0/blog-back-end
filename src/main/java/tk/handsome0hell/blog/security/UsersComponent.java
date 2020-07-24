package tk.handsome0hell.blog.security;

import tk.handsome0hell.blog.pojo.User;
import java.util.List;

public interface UsersComponent {
  Boolean IsLogined();
  User Login(User user);
  Boolean Logout();
  List<User> GetUsers();
  Boolean AddUser(User user);
  Boolean DeleteUserById(Integer id);
  Boolean UpdateUser(User user);
};
