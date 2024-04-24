package com.kaushikmanivannan.hireandseek.dao;

import com.kaushikmanivannan.hireandseek.model.Candidate;
import com.kaushikmanivannan.hireandseek.model.User;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CandidateDAOImpl implements CandidateDAO {

    private final EntityManager entityManager;

    @Autowired
    public CandidateDAOImpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }

    @Override
    @Transactional
    public void save(Candidate candidate) {
        entityManager.persist(candidate);
    }

    @Override
    public Candidate findCandidateByUser(User user) {
        return entityManager.createQuery("SELECT c FROM Candidate c WHERE c.user = :user", Candidate.class)
                .setParameter("user", user)
                .getSingleResult();
    }

    @Override
    @Transactional
    public void update(Candidate candidate) {
        entityManager.merge(candidate);
    }

    @Override
    @Transactional
    public void delete(Candidate candidate) {
        entityManager.remove(candidate);
    }
}
