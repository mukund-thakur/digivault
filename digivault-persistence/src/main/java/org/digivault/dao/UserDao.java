package org.digivault.dao;

import io.dropwizard.hibernate.AbstractDAO;
import org.digivault.entity.User;
import org.hibernate.SessionFactory;

public class UserDao extends AbstractDAO<User> {

  public UserDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public User create(User user) {
    return persist(user);
  }

  public User getById(Long id) {
    return get(id);
  }
}
