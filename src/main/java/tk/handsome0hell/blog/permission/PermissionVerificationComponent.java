package tk.handsome0hell.blog.permission;

import tk.handsome0hell.blog.pojo.PermissionsType;

public interface PermissionVerificationComponent {
  Boolean HasPermission(
      UserIdRepository repository, PermissionsType permission);
}
