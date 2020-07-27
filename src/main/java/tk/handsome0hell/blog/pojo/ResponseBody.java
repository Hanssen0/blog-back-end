package tk.handsome0hell.blog.pojo;

public class ResponseBody {
  private Object data;
  private String error_type;
  private String error_information;
  public ResponseBody() {}
  public ResponseBody(Object data) { this.data = data; }
  public Object getData() { return data; }
  public String getError_type() { return error_type; }
  public String getError_information() { return error_information; }
  public void SetError(ErrorsType error_type, String error_information) {
    this.data = null;
    this.error_type = error_type.toString();
    this.error_information = error_information;
  }
  public void SetError(ErrorsType error_type) {
    this.SetError(error_type, null);
  }
}
