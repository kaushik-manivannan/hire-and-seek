package com.kaushikmanivannan.hireandseek.dao;

import com.kaushikmanivannan.hireandseek.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDAOImpl implements UserDAO {

    private final EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public User findByEmail(String email) {
        try {
            TypedQuery<User> query = entityManager.createQuery("FROM User WHERE email=:email", User.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public User findByPhone(String phone) {
        try {
            TypedQuery<User> query = entityManager.createQuery("FROM User WHERE phone=:phone", User.class);
            query.setParameter("phone", phone);
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public void update(User user) {
        entityManager.merge(user);
    }
}
