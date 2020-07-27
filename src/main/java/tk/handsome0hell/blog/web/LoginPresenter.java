package tk.handsome0hell.blog.web;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.RequestPredicate;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.servlet.function.ServerRequest;

import tk.handsome0hell.blog.pojo.ResponseBody;
import tk.handsome0hell.blog.pojo.ErrorsType;
import tk.handsome0hell.blog.pojo.User;
import tk.handsome0hell.blog.users.UsersComponent;
import tk.handsome0hell.blog.permission.SessionUserIdRepository;
import tk.handsome0hell.blog.permission.LoginComponent;
import tk.handsome0hell.blog.permission.PermissionComponent;

import javax.servlet.ServletException;
import java.io.IOException;

import java.util.List;
import java.util.LinkedList;

@Component
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
  public List<Route> BuildRoutes() {
    final String root = "/login";
    final List<Route> routes = new LinkedList<Route>();
    routes.add(new Route(RequestPredicates.GET(root), this::IsLogin));
    routes.add(new Route(RequestPredicates.PUT(root), this::Login));
    routes.add(new Route(RequestPredicates.DELETE(root), this::Logout));
    return routes;
  }
  ServerResponse IsLogin(ServerRequest request) {
    return ServerResponse.ok().body(new ResponseBody(
      permission_component.HasLogined(
        new SessionUserIdRepository(request.session())
      )
    ));
  };
  ServerResponse Login(ServerRequest request) {
    User user;
    // TODO: Handle wrong entity errors
    try { user = request.body(User.class); }
    catch (ServletException expection) {
      return ServerResponse.unprocessableEntity().build();
    } catch (IOException expection) {
      return ServerResponse.unprocessableEntity().build();
    }
    ResponseBody response = new ResponseBody();
    User matched_user = users_component.GetUserByUsernameAndPassword(user);
    if (matched_user == null) {
      response.SetError(ErrorsType.kAccountNotFound);
    } else {
      login_component.Login(
        new SessionUserIdRepository(request.session()),
        matched_user
      );
    }
    return ServerResponse.ok().body(response);
  };
  ServerResponse Logout(ServerRequest request) {
    login_component.Logout(new SessionUserIdRepository(request.session()));
    return ServerResponse.ok().body(new ResponseBody());
  };
}
