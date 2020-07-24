package tk.handsome0hell.blog.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tk.handsome0hell.blog.pojo.User;
import tk.handsome0hell.blog.user.UsersComponent;
import tk.handsome0hell.blog.permission.UserIdRepository;
import tk.handsome0hell.blog.permission.SessionUserIdRepository;
import tk.handsome0hell.blog.permission.LoginComponent;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/login")
public class LoginPresenter {
  private UsersComponent users_component;
  private LoginComponent login_component;
  public LoginPresenter(
      UsersComponent users_component,
      LoginComponent login_component) {
    this.users_component = users_component;
    this.login_component = login_component;
  }
  @GetMapping("")
  public Boolean IsLogin(HttpSession session) {
    UserIdRepository user_id_repository = new SessionUserIdRepository(session);
    return login_component.HasLogined(user_id_repository);
  };
  @PutMapping("")
  public Boolean Login(@RequestBody User user, HttpSession session) {
    User matched_user = users_component.GetUserByUsernameAndPassword(user);
    if (matched_user == null) return false;
    UserIdRepository user_id_repository = new SessionUserIdRepository(session);
    user_id_repository.setUserId(matched_user.getId());
    return true;
  };
  @DeleteMapping("")
  public Boolean Logout(HttpSession session) {
    UserIdRepository user_id_repository = new SessionUserIdRepository(session);
    user_id_repository.setUserId(null);
    return true;
  };
}