package tk.handsome0hell.blog.roles;

import tk.handsome0hell.blog.pojo.Role;
import tk.handsome0hell.blog.pojo.Permission;
import java.util.List;

public interface RolesComponent {
  List<Role> GetRoles();
  List<Permission> GetPermissionsOfRole(Role role);
  Boolean AddPermissionToRole(Permission permission, Role role);
  Boolean DeletePermissionFromRole(Permission permission, Role role);
}
