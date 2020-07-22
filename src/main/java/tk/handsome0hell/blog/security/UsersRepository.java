package tk.handsome0hell.blog.security;

import tk.handsome0hell.blog.pojo.User;

interface UsersRepository {
  Boolean ValidateUser(User user);
};
