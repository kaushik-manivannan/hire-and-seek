package com.kaushikmanivannan.hireandseek.dao;

import com.kaushikmanivannan.hireandseek.model.Employer;
import com.kaushikmanivannan.hireandseek.model.User;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class EmployerDAOImpl implements EmployerDAO {

    private final EntityManager entityManager;

    @Autowired
    public EmployerDAOImpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }

    @Override
    @Transactional
    public void save(Employer employer) {
        entityManager.persist(employer);
    }

    @Override
    public Employer findEmployerByUser(User user) {
        return entityManager.createQuery("SELECT e FROM Employer e WHERE e.user = :user", Employer.class)
                .setParameter("user", user)
                .getSingleResult();
    }

    @Override
    @Transactional
    public void update(Employer employer) {
        entityManager.merge(employer);
    }

    @Override
    @Transactional
    public void delete(Employer employer) {
        entityManager.remove(employer);
    }
}
