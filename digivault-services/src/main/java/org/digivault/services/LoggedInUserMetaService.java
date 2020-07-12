package org.digivault.services;

import org.digivault.entity.LoggedInUser;

public interface LoggedInUserMetaService {

  LoggedInUser addLoginInfo(LoggedInUser loggedInUser);

  LoggedInUser getLogInInfo(Long userId);

  void deleteLoginInfo(Long loggedInUserId);
}
