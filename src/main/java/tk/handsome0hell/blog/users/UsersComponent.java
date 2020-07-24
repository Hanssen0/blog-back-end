package tk.handsome0hell.blog.users;

import tk.handsome0hell.blog.pojo.User;
import java.util.List;

public interface UsersComponent {
  User GetUserByUsernameAndPassword(User user);
  List<User> GetUsers();
  Boolean AddUser(User user);
  Boolean DeleteUserById(Integer id);
  Boolean UpdateUser(User user);
};
