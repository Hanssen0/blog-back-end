package tk.handsome0hell.blog.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tk.handsome0hell.blog.pojo.ResponseBody;
import tk.handsome0hell.blog.pojo.ErrorsType;
import tk.handsome0hell.blog.pojo.User;
import tk.handsome0hell.blog.users.UsersComponent;
import tk.handsome0hell.blog.permission.SessionUserIdRepository;
import tk.handsome0hell.blog.permission.LoginComponent;
import tk.handsome0hell.blog.permission.PermissionComponent;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/login")
public class LoginPresenter {
  private UsersComponent users_component;
  private LoginComponent login_component;
  private PermissionComponent permission_component;
  public LoginPresenter(
      UsersComponent users_component,
      LoginComponent login_component,
      PermissionComponent permission_component) {
    this.users_component = users_component;
    this.login_component = login_component;
    this.permission_component = permission_component;
  }
  @GetMapping("")
  public ResponseBody IsLogin(HttpSession session) {
    return new ResponseBody(
      permission_component.HasLogined(
        new SessionUserIdRepository(session)
      )
    );
  };
  @PutMapping("")
  public ResponseBody Login(@RequestBody User user, HttpSession session) {
    ResponseBody response = new ResponseBody();
    User matched_user = users_component.GetUserByUsernameAndPassword(user);
    if (matched_user == null) {
      response.SetError(ErrorsType.kAccountNotFound);
      return response;
    }
    login_component.Login(new SessionUserIdRepository(session), matched_user);
    return response;
  };
  @DeleteMapping("")
  public ResponseBody Logout(HttpSession session) {
    login_component.Logout(new SessionUserIdRepository(session));
    return new ResponseBody();
  };
}
