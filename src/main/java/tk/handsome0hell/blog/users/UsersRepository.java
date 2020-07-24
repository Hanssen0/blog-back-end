package tk.handsome0hell.blog.users;

import tk.handsome0hell.blog.pojo.User;
import java.util.List;

interface UsersRepository {
  User GetUserByUsernameAndPassword(User user);
  List<User> GetUsers();
  Integer AddUser(User user);
  Integer DeleteUserById(Integer id);
  Integer UpdateUser(User user);
};
