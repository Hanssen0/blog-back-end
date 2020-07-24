package tk.handsome0hell.blog.permission;

import tk.handsome0hell.blog.pojo.Role;
import tk.handsome0hell.blog.pojo.Permission;
import java.util.List;

class DatabasePermissionComponent implements PermissionManagementComponent {
  private PermissionManagementRepository permission_management_repository;
  DatabasePermissionComponent(
      PermissionManagementRepository permission_management_repository) {
    this.permission_management_repository = permission_management_repository;
  }
  @Override
  public List<Role> GetRoles() {
    return permission_management_repository.GetRoles();
  }
  @Override
  public List<Permission> GetPermissionsOfRole(Role role) {
    return permission_management_repository.GetPermissionsOfRole(role);
  }
  @Override
  public Boolean AddPermissionToRole(Permission permission, Role role) {
    return permission_management_repository
             .InsertPermissionRoleRelationship(permission, role);
  }
  @Override
  public Boolean DeletePermissionFromRole(Permission permission, Role role) {
    return permission_management_repository
             .DeletePermissionRoleRelationship(permission, role);
  }
}
