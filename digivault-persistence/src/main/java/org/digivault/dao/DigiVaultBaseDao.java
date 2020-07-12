package org.digivault.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.dropwizard.hibernate.AbstractDAO;
import javax.persistence.Query;
import org.hibernate.SessionFactory;

public abstract class DigiVaultBaseDao<E> extends AbstractDAO<E> {

  public DigiVaultBaseDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public E getEntity(Long id) {
    return super.get(id);
  }

  public E addEntity(E e) {
    return persist(e);
  }

  protected Optional<E> getEntity(String queryString, String key, Object value) {
    Query query = currentSession().createNamedQuery(queryString);
    query.setParameter(key, value);
    List resultList = query.getResultList();
    List<E> userList = new ArrayList<E>();
    for (Object obj : resultList) {
      userList.add((E) obj);
    }
    return userList.size() == 0 ? Optional.empty()
            : Optional.of(userList.get(0));
  }
}
