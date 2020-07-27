package tk.handsome0hell.blog.web;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.RequestPredicate;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.servlet.function.ServerRequest;

import tk.handsome0hell.blog.pojo.PermissionsType;
import tk.handsome0hell.blog.pojo.Role;
import tk.handsome0hell.blog.pojo.Permission;
import tk.handsome0hell.blog.roles.RolesComponent;
import tk.handsome0hell.blog.pojo.ResponseBody;

import javax.servlet.ServletException;
import java.io.IOException;

import java.util.List;
import java.util.LinkedList;

@Component
public class RolesPresenter {
  private RolesComponent roles_component;
  public RolesPresenter(RolesComponent roles_component) {
    this.roles_component = roles_component;
  }
  public List<Route> BuildRoutes() {
    final String root = "/roles";
    final List<Route> routes = new LinkedList<Route>();
    routes.add(
      new Route(RequestPredicates.GET(root), this::GetRoles)
        .AddRequiredPermission(PermissionsType.kGetRoles));
    routes.add(
      new Route(RequestPredicates.GET(root + "/{id}"),
                this::GetPermissionsOfRole)
        .AddRequiredPermission(PermissionsType.kGetPermissionsOfRole));
    routes.add(
      new Route(RequestPredicates.POST(root + "/{id}"),
                this::AddPermissionToRole)
        .AddRequiredPermission(PermissionsType.kAddPermissionsToTole));
    routes.add(
      new Route(RequestPredicates.DELETE(root + "/{role_id}/{permission_id}"),
                this::DeletePermissionFromRole)
        .AddRequiredPermission(PermissionsType.kDeletePermissionsFromRole));
    return routes;
  }
  ServerResponse GetRoles(ServerRequest request) {
    return ServerResponse.ok()
             .body(new ResponseBody(roles_component.GetRoles()));
  }
  ServerResponse GetPermissionsOfRole(ServerRequest request) {
    // TODO: Handle number parsing error
    Integer role_id = Integer.parseInt(request.pathVariable("id"));
    Role role = new Role();
    role.setId(role_id);
    return ServerResponse.ok()
             .body(new ResponseBody(roles_component.GetPermissionsOfRole(role)));
  }
  ServerResponse AddPermissionToRole(ServerRequest request) {
    // TODO: Handle number parsing error
    Integer role_id = Integer.parseInt(request.pathVariable("id"));
    Role role = new Role();
    role.setId(role_id);
    Permission permission;
    // TODO: Handle wrong entity errors
    try { permission = request.body(Permission.class); }
    catch (ServletException expection) {
      return ServerResponse.unprocessableEntity().build();
    } catch (IOException expection) {
      return ServerResponse.unprocessableEntity().build();
    }
    return
      ServerResponse.ok()
        .body(
          new ResponseBody(
            roles_component.AddPermissionToRole(permission, role)
          )
        );
  }
  ServerResponse DeletePermissionFromRole(ServerRequest request) {
    // TODO: Handle number parsing error
    Integer role_id = Integer.parseInt(request.pathVariable("role_id"));
    Integer permission_id =
      Integer.parseInt(request.pathVariable("permission_id"));
    Role role = new Role();
    role.setId(role_id);
    Permission permission = new Permission();
    permission.setId(permission_id);
    return
      ServerResponse.ok()
        .body(
          new ResponseBody(
            roles_component.DeletePermissionFromRole(permission, role)
          )
        );
  }
}
