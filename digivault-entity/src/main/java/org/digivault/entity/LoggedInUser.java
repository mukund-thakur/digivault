package org.digivault.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.company.digivault.queries.TokenNamedQueries;

@Entity
@Table(name = "digivault_loggedin_user")
@NamedQueries(
        @NamedQuery(name = TokenNamedQueries.DELETE_TOKEN_OF_USER_KEY, query = TokenNamedQueries.DELETE_TOKEN_OF_USER_VALUE)
)
public class LoggedInUser {

  @Id
  @Column(name = "user_id")
  private Long userId;

  @Column(name = "token")
  private String token;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
