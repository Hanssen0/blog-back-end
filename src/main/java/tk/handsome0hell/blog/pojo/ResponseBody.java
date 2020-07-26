package tk.handsome0hell.blog.pojo;

import tk.handsome0hell.blog.permission.PermissionComponent;
import tk.handsome0hell.blog.permission.UserIdRepository;

public class ResponseBody {
  private Object data;
  private String error_type;
  private String error_information;
  public ResponseBody() {}
  public ResponseBody(Object data) { this.data = data; }
  public Object getData() { return data; }
  public String getError_type() { return error_type; }
  public String getError_information() { return error_information; }
  public Boolean VerifyPermission(
      PermissionComponent permission_component,
      UserIdRepository repository,
      PermissionsType permission_type) {
    if (!permission_component.HasLogined(repository)) {
      SetNotLoggedInError();
      return false;
    }
    if (!permission_component
        .HasPermission(repository, PermissionsType.kGetUsersList)) {
      SetNoPermissionError(permission_type);
      return false;
    }
    return true;
  }
  public void SetError(ErrorsType error_type, String error_information) {
    this.data = null;
    this.error_type = error_type.toString();
    this.error_information = error_information;
  }
  public void SetError(ErrorsType error_type) {
    this.SetError(error_type, null);
  }
  public void SetNotLoggedInError() {
    this.data = null;
    this.error_type = ErrorsType.kNotLoggedIn.toString();
    this.error_information = null;
  }
  public void SetNoPermissionError(PermissionsType permission) {
    this.data = null;
    this.error_type = ErrorsType.kNoPermission.toString();
    this.error_information = permission.toString();
  }
}
