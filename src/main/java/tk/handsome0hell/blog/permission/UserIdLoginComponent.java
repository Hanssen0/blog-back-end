package tk.handsome0hell.blog.permission;

import tk.handsome0hell.blog.pojo.User;

class UserIdLoginComponent implements LoginComponent {
  @Override
  public Boolean HasLogined(UserIdRepository repository) {
    return (repository.getUserId() != null);
  }
  @Override
  public void Login(UserIdRepository repository, User user) {
    repository.setUserId(user.getId());
  }
  @Override
  public void Logout(UserIdRepository repository) {
    repository.setUserId(null);
  }
};
