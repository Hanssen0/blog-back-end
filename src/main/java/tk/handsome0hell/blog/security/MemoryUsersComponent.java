package tk.handsome0hell.blog.security;

import tk.handsome0hell.blog.pojo.User;

class MemoryUsersComponent implements UsersComponent {
  private Boolean is_logined;
  public MemoryUsersComponent() {
    is_logined = false;
  };
  @Override
  public Boolean IsLogined() {
    return is_logined;
  };
  @Override
  public Boolean Login(User user) {
    is_logined =  user.getUsername().equals("user") &&
                  user.getPassword().equals("password");
    return is_logined;
  };
  @Override
  public Boolean Logout() {
    is_logined = false;
    return true;
  };
};
