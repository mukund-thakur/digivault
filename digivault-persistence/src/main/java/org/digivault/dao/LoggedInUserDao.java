package org.digivault.dao;

import javax.persistence.Query;
import org.company.digivault.queries.TokenNamedQueries;
import org.digivault.entity.LoggedInUser;
import org.hibernate.SessionFactory;

public class LoggedInUserDao extends DigiVaultBaseDao<LoggedInUser> {

  public LoggedInUserDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public void deleteLoggedInUser(Long userId) {
    Query query = currentSession().createNamedQuery(TokenNamedQueries.DELETE_TOKEN_OF_USER_KEY);
    query.setParameter("userId", userId);
    query.executeUpdate();
  }
}
