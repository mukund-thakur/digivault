package org.digivault.dao;

import java.util.Optional;

import org.company.digivault.queries.UserNamedQueries;
import org.digivault.entity.User;
import org.hibernate.SessionFactory;

public class UserDao extends DigiVaultBaseDao<User> {

  public UserDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public User create(User user) {
    return persist(user);
  }

  public User getById(Long id) {
    return get(id);
  }

  public Optional<User> getByEmail(String email) {
    return getEntity(UserNamedQueries.GET_USER_BY_EMAIL_KEY, "email", email);
  }

  public Optional<User> getByContactNum(String contact) {
    return getEntity(UserNamedQueries.GET_USER_BY_CONTACT_KEY, "contactNum", contact);
  }

  public User updateUser(User updatedUser) {
    return persist(updatedUser);
  }
}
