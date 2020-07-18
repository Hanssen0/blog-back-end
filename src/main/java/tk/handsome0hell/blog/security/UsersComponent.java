package tk.handsome0hell.blog.security;

import tk.handsome0hell.blog.pojo.User;

public interface UsersComponent {
  Boolean IsLogined();
  Boolean Login(User user);
  Boolean Logout();
};
