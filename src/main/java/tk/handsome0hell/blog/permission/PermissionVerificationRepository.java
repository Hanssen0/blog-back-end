package tk.handsome0hell.blog.permission;

import java.util.List;

public interface PermissionVerificationRepository {
  Boolean IsUserHasPermission(Integer user_id, Integer permission_id);
}
