package tk.handsome0hell.blog.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tk.handsome0hell.blog.pojo.ResponseBody;
import tk.handsome0hell.blog.pojo.ErrorsType;
import tk.handsome0hell.blog.pojo.User;
import tk.handsome0hell.blog.users.UsersComponent;
import tk.handsome0hell.blog.pojo.PermissionsType;
import tk.handsome0hell.blog.permission.SessionUserIdRepository;
import tk.handsome0hell.blog.permission.PermissionComponent;
import javax.servlet.http.HttpSession;
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
  private PermissionComponent permission_component;
  public UsersPresenter(
      UsersComponent users_component,
      PermissionComponent permission_component) {
    this.users_component = users_component;
    this.permission_component = permission_component;
  }
  @GetMapping("")
  public ResponseBody GetUsers(HttpSession session) {
    List<UserWithoutPassword> response_users =
      new LinkedList<UserWithoutPassword>();
    ResponseBody response = new ResponseBody(response_users);
    if (!response.VerifyPermission(permission_component,
                                   new SessionUserIdRepository(session),
                                   PermissionsType.kGetUsersList)) {
      return response;
    }
    List<User> users = users_component.GetUsers();
    for (User user : users) {
      response_users.add(new UserWithoutPassword(user));
    }
    return response;
  };
  @PostMapping("")
  public ResponseBody AddUser(
      @RequestBody User user, HttpSession session) {
    ResponseBody response = new ResponseBody();
    if (!response.VerifyPermission(permission_component,
                                   new SessionUserIdRepository(session),
                                   PermissionsType.kAddUser)) {
      return response;
    }
    // TODO: handle adding user error
    users_component.AddUser(user);
    return response;
  };
  @DeleteMapping("{id}")
  public ResponseBody DeleteUserById(
      @PathVariable("id") Integer id,
      HttpSession session) {
    ResponseBody response = new ResponseBody();
    if (!response.VerifyPermission(permission_component,
                                   new SessionUserIdRepository(session),
                                   PermissionsType.kDeleteUser)) {
      return response;
    }
    // TODO: handle deleting user error
    users_component.DeleteUserById(id);
    return response;
  };
  @PutMapping("{id}")
  public ResponseBody UpdateUser(
      @PathVariable("id") Integer id,
      @RequestBody User user,
      HttpSession session) {
    ResponseBody response = new ResponseBody();
    if (!response.VerifyPermission(permission_component,
                                   new SessionUserIdRepository(session),
                                   PermissionsType.kModifyUser)) {
      return response;
    }
    user.setId(id);
    // TODO: handle modifying user error
    users_component.UpdateUser(user);
    return response;
  };
}
