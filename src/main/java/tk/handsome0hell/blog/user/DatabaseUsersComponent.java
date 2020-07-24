package tk.handsome0hell.blog.user;

import tk.handsome0hell.blog.pojo.User;
import java.util.List;

class DatabaseUsersComponent implements UsersComponent {
  private UsersRepository users_repository;
  public DatabaseUsersComponent(UsersRepository users_repository) {
    this.users_repository = users_repository;
  };
  @Override
  public User GetUserByUsernameAndPassword(User user) {
    return users_repository.GetUserByUsernameAndPassword(user);
  };
  @Override
  public List<User> GetUsers() {
    return users_repository.GetUsers();
  }
  @Override
  public Boolean AddUser(User user) {
    Integer number_of_new_rows = users_repository.AddUser(user);
    return (number_of_new_rows == 1);
  }
  @Override
  public Boolean DeleteUserById(Integer id) {
    Integer number_of_deleted_rows = users_repository.DeleteUserById(id);
    return (number_of_deleted_rows == 1);
  }
  @Override
  public Boolean UpdateUser(User user) {
    Integer number_of_updated_rows = users_repository.UpdateUser(user);
    return (number_of_updated_rows == 1);
  }
};
