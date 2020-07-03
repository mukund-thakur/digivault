package org.digivault.services.impl;

import java.util.Optional;

import org.digivault.dao.UserDao;
import org.digivault.entity.User;
import org.digivault.services.UserMetaService;

public class RDBMSUserMetaServiceImpl implements UserMetaService {

  private UserDao userDao;

  public RDBMSUserMetaServiceImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  public User createUser(User user) {
    return userDao.create(user);
  }

  public User getUserById(long id) {
    return userDao.getById(id);
  }

  public User updateUser(User updatedUser) {
    return userDao.updateUser(updatedUser);
  }

  public Optional<User> getUserByEmail(String email) {
    return userDao.getByEmail(email);
  }

  public Optional<User> getUserByContact(String contact) {
    return userDao.getByContactNum(contact);
  }
}
