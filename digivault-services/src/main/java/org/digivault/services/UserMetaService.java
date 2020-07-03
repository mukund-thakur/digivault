package org.digivault.services;

import java.util.Optional;

import org.digivault.entity.User;

public interface UserMetaService {

  User createUser(User user);

  User getUserById(long id);

  User updateUser(User updatedUser);

  Optional<User> getUserByEmail(final String email);

  Optional<User> getUserByContact(final String contact);

}
