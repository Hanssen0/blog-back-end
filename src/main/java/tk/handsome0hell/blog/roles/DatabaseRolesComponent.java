package tk.handsome0hell.blog.roles;

import tk.handsome0hell.blog.pojo.Role;
import tk.handsome0hell.blog.pojo.Permission;
import java.util.List;

class DatabaseRolesComponent implements RolesComponent {
  private RolesRepository roles_repository;
  DatabaseRolesComponent(RolesRepository roles_repository) {
    this.roles_repository = roles_repository;
  }
  @Override
  public List<Role> GetRoles() {
    return roles_repository.GetRoles();
  }
  @Override
  public List<Permission> GetPermissionsOfRole(Role role) {
    return roles_repository.GetPermissionsOfRole(role);
  }
  @Override
  public Boolean AddPermissionToRole(Permission permission, Role role) {
    return roles_repository.InsertPermissionRoleRelationship(permission, role);
  }
  @Override
  public Boolean DeletePermissionFromRole(Permission permission, Role role) {
    return roles_repository.DeletePermissionRoleRelationship(permission, role);
  }
}
