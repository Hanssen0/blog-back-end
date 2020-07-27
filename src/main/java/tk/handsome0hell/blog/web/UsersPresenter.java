package tk.handsome0hell.blog.web;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.RequestPredicate;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.servlet.function.ServerRequest;

import tk.handsome0hell.blog.pojo.ResponseBody;
import tk.handsome0hell.blog.pojo.User;
import tk.handsome0hell.blog.users.UsersComponent;
import tk.handsome0hell.blog.pojo.PermissionsType;
import tk.handsome0hell.blog.permission.PermissionComponent;

import javax.servlet.ServletException;
import java.io.IOException;

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

@Component
public class UsersPresenter {
  private UsersComponent users_component;
  private PermissionComponent permission_component;
  public UsersPresenter(
      UsersComponent users_component,
      PermissionComponent permission_component) {
    this.users_component = users_component;
    this.permission_component = permission_component;
  }
  public List<Route> BuildRoutes() {
    final String root = "/users";
    final List<Route> routes = new LinkedList<Route>();
    routes.add(
      new Route(RequestPredicates.GET(root), this::GetUsers)
        .AddRequiredPermission(PermissionsType.kGetUsersList));
    routes.add(
      new Route(RequestPredicates.POST(root), this::AddUser)
        .AddRequiredPermission(PermissionsType.kAddUser));
    routes.add(
      new Route(RequestPredicates.DELETE(root + "/{id}"), this::DeleteUserById)
        .AddRequiredPermission(PermissionsType.kDeleteUser));
    routes.add(
      new Route(RequestPredicates.PUT(root + "/{id}"), this::UpdateUser)
        .AddRequiredPermission(PermissionsType.kModifyUser));
    return routes;
  }
  ServerResponse GetUsers(ServerRequest request) {
    List<UserWithoutPassword> response_users =
      new LinkedList<UserWithoutPassword>();
    ResponseBody response = new ResponseBody(response_users);
    List<User> users = users_component.GetUsers();
    for (User user : users) {
      response_users.add(new UserWithoutPassword(user));
    }
    return ServerResponse.ok().body(response);
  };
  ServerResponse AddUser(ServerRequest request) {
    User user;
    // TODO: Handle wrong entity errors
    try { user = request.body(User.class); }
    catch (ServletException expection) {
      return ServerResponse.unprocessableEntity().build();
    } catch (IOException expection) {
      return ServerResponse.unprocessableEntity().build();
    }
    // TODO: handle adding user error
    users_component.AddUser(user);
    return ServerResponse.ok().body(new ResponseBody());
  };
  ServerResponse DeleteUserById(ServerRequest request) {
    // TODO: Handle number parsing error
    Integer id = Integer.parseInt(request.pathVariable("id"));
    // TODO: handle deleting user error
    users_component.DeleteUserById(id);
    return ServerResponse.ok().body(new ResponseBody());
  };
  ServerResponse UpdateUser(ServerRequest request) {
    User user;
    // TODO: Handle wrong entity errors
    try { user = request.body(User.class); }
    catch (ServletException expection) {
      return ServerResponse.unprocessableEntity().build();
    } catch (IOException expection) {
      return ServerResponse.unprocessableEntity().build();
    }
    // TODO: Handle number parsing error
    Integer id = Integer.parseInt(request.pathVariable("id"));
    user.setId(id);
    // TODO: handle modifying user error
    users_component.UpdateUser(user);
    return ServerResponse.ok().body(new ResponseBody());
  };
}
