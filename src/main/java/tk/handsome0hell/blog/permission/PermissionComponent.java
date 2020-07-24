package tk.handsome0hell.blog.permission;

import tk.handsome0hell.blog.pojo.User;

public interface PermissionComponent {
  Boolean HasLogined(UserIdRepository repository);
};
