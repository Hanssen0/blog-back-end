package tk.handsome0hell.blog.user;

import javax.servlet.http.HttpSession;

public class SessionUserIdRepository implements UserIdRepository {
  final private String kUserIdKey = "UserId";
  private HttpSession session;
  public SessionUserIdRepository(HttpSession session) {this.session = session;}
  @Override
  public void setUserId(Integer id) {
    session.setAttribute(kUserIdKey, id);
  }
  @Override
  public Integer getUserId() {
    return (Integer)(session.getAttribute(kUserIdKey));
  }
};
