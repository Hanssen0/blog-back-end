package tk.handsome0hell.blog.permission;

import tk.handsome0hell.blog.pojo.Role;
import tk.handsome0hell.blog.pojo.Permission;
import tk.handsome0hell.blog.pojo.PermissionsType;
import java.util.List;

class DatabasePermissionComponent
  implements PermissionManagementComponent, PermissionVerificationComponent {
  private PermissionManagementRepository permission_management_repository;
  private PermissionVerificationRepository permission_verification_repository;
  DatabasePermissionComponent(
      PermissionManagementRepository permission_management_repository,
      PermissionVerificationRepository permission_verification_repository) {
    this.permission_management_repository = permission_management_repository;
    this.permission_verification_repository =
      permission_verification_repository;
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
  @Override
  public Boolean HasPermission(
      UserIdRepository repository, PermissionsType permission) {
    return permission_verification_repository
      .IsUserHasPermission(repository.getUserId(), permission.getId());
  }
  @Override
  public Boolean HasLogined(UserIdRepository repository) {
    return (repository.getUserId() != null);
  }
}
