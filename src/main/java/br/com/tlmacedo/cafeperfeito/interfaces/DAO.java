package br.com.tlmacedo.cafeperfeito.interfaces;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public interface DAO<T, I extends Serializable> {

//    public T persiste(T entity);

    public T save(T entity);

    public void remove(T entity);

    public T getById(Class<T> classe, I pk);

    public List<T> getAll(Class<T> classe);

    public EntityManager getEntityManager();
}
