package tk.handsome0hell.blog.security;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;
import tk.handsome0hell.blog.pojo.User;

@Mapper
interface MybatisUsersRepository extends UsersRepository {
  @Override
  @Select("SELECT EXISTS(SELECT * FROM accounts WHERE username = #{username} and password = #{password} LIMIT 1);")
  Boolean ValidateUser(User user);
};
