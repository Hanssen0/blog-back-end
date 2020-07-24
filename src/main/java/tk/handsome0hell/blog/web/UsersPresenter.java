package tk.handsome0hell.blog.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tk.handsome0hell.blog.pojo.User;
import tk.handsome0hell.blog.user.UsersComponent;
import java.util.List;
import java.util.LinkedList;

class UserWithoutPassword {
  private User user;
  public UserWithoutPassword(User user) {
    this.user = user;
  }
  public Integer getId() {return user.getId();}
  public String getUsername() {return user.getUsername();}
  public Integer getRole_id() {return user.getRole_id();}
};

@RestController
@RequestMapping("/users")
public class UsersPresenter {
  private UsersComponent users_component;
  public UsersPresenter(UsersComponent users_component) {
    this.users_component = users_component;
  }
  @GetMapping("")
  public List<UserWithoutPassword> GetUsers() {
    List<UserWithoutPassword> response_users =
      new LinkedList<UserWithoutPassword>();
    List<User> users = users_component.GetUsers();
    for (User user : users) {
      response_users.add(new UserWithoutPassword(user));
    }
    return response_users;
  };
  @PostMapping("")
  public Boolean AddUser(@RequestBody User user) {
    return users_component.AddUser(user);
  };
  @DeleteMapping("{id}")
  public Boolean DeleteUserById(@PathVariable("id") Integer id) {
    return users_component.DeleteUserById(id);
  };
  @PutMapping("{id}")
  public Boolean UpdateUser(@PathVariable("id") Integer id,
                         @RequestBody User user) {
    user.setId(id);
    return users_component.UpdateUser(user);
  };
}
