package br.com.cafeperfeito.sidtmcafe.interfaces;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.interfaces.DAO;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.Serializable;
import java.util.List;

public abstract class DAOImpl<T, I extends Serializable> implements DAO<T, I> {

    private ConnectionFactory conexao;

    @Override
    public T save(T entity) {

        T saved = null;

        getEntityManager().getTransaction().begin();
        saved = getEntityManager().merge(entity);
        getEntityManager().getTransaction().commit();

        return saved;
    }

    @Override
    public void remove(T entity) {
        getEntityManager().getTransaction().begin();
        getEntityManager().remove(entity);
        getEntityManager().getTransaction().commit();

    }

    @Override
    public T getById(Class<T> classe, I pk) {

        try {
            return getEntityManager().find(classe, pk);
        } catch (NoResultException e) {
            return null;
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> getAll(Class<T> classe) {

        return getEntityManager().createQuery("select o from " + classe.getSimpleName() + " o").getResultList();
    }

    @Override
    public EntityManager getEntityManager() {

        if (conexao == null) {
            conexao = new ConnectionFactory();
        }
        return conexao.getEntityManager();
    }

}