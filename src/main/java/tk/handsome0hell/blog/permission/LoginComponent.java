package tk.handsome0hell.blog.permission;

import tk.handsome0hell.blog.pojo.User;

public interface LoginComponent {
  Boolean HasLogined(UserIdRepository repository);
};
