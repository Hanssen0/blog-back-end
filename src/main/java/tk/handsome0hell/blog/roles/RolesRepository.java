package tk.handsome0hell.blog.roles;

import tk.handsome0hell.blog.pojo.Role;
import tk.handsome0hell.blog.pojo.Permission;
import java.util.List;

public interface RolesRepository {
  List<Role> GetRoles();
  List<Permission> GetPermissionsOfRole(Role role);
  Boolean InsertPermissionRoleRelationship(Permission permission, Role role);
  Boolean DeletePermissionRoleRelationship(Permission permission, Role role);
}
