package org.digivault.services;

import org.digivault.entity.User;

public interface UserMetaService {

  User createUser(User user);

  User getUserById(long id);

}
