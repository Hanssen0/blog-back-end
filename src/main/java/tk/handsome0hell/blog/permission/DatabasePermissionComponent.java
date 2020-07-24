package tk.handsome0hell.blog.permission;

import tk.handsome0hell.blog.pojo.PermissionsType;
import java.util.List;

class DatabasePermissionComponent
  implements PermissionVerificationComponent {
  private PermissionVerificationRepository permission_verification_repository;
  DatabasePermissionComponent(
      PermissionVerificationRepository permission_verification_repository) {
    this.permission_verification_repository =
      permission_verification_repository;
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
