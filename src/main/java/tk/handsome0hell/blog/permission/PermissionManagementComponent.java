package tk.handsome0hell.blog.permission;

import tk.handsome0hell.blog.pojo.Role;
import tk.handsome0hell.blog.pojo.Permission;
import java.util.List;

public interface PermissionManagementComponent {
  List<Role> GetRoles();
  List<Permission> GetPermissionsOfRole(Role role);
  Boolean AddPermissionToRole(Permission permission, Role role);
  Boolean DeletePermissionFromRole(Permission permission, Role role);
}
