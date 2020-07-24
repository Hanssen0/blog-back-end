package tk.handsome0hell.blog.permission;

import java.util.List;

public interface PermissionRepository {
  Boolean IsUserHasPermission(Integer user_id, Integer permission_id);
}
