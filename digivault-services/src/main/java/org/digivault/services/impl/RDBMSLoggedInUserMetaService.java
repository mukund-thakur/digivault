package org.digivault.services.impl;

import org.digivault.dao.LoggedInUserDao;
import org.digivault.entity.LoggedInUser;
import org.digivault.services.LoggedInUserMetaService;

public class RDBMSLoggedInUserMetaService implements LoggedInUserMetaService {

  private LoggedInUserDao loggedInUserDao;

  public RDBMSLoggedInUserMetaService(LoggedInUserDao loggedInUserDao) {
    this.loggedInUserDao = loggedInUserDao;
  }

  @Override
  public LoggedInUser addLoginInfo(LoggedInUser loggedInUser) {
    return loggedInUserDao.addEntity(loggedInUser);
  }

  @Override
  public LoggedInUser getLogInInfo(Long userId) {
    return loggedInUserDao.getEntity(userId);
  }

  @Override
  public void deleteLoginInfo(Long loggedInUserId) {
    loggedInUserDao.deleteLoggedInUser(loggedInUserId);
  }
}
