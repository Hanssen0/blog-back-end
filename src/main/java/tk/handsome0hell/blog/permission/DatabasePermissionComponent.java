package tk.handsome0hell.blog.permission;

import tk.handsome0hell.blog.pojo.PermissionsType;
import java.util.List;

class DatabasePermissionComponent
  implements PermissionComponent {
  private PermissionRepository permission_repository;
  DatabasePermissionComponent(PermissionRepository permission_repository) {
    this.permission_repository = permission_repository;
  }
  @Override
  public Boolean HasPermission(
      UserIdRepository repository, PermissionsType permission) {
    return permission_repository
      .IsUserHasPermission(repository.getUserId(), permission.getId());
  }
  @Override
  public Boolean HasLogined(UserIdRepository repository) {
    return (repository.getUserId() != null);
  }
}
