package com.kaushikmanivannan.hireandseek.dao;

import com.kaushikmanivannan.hireandseek.model.Application;
import com.kaushikmanivannan.hireandseek.model.Candidate;
import com.kaushikmanivannan.hireandseek.model.Feedback;
import com.kaushikmanivannan.hireandseek.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class FeedbackDAOImpl implements FeedbackDAO {

    private final EntityManager entityManager;

    @Autowired
    public FeedbackDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Feedback feedback) {
        entityManager.persist(feedback);
    }

    @Override
    public Feedback findFeedbackById(Long id) {
        return entityManager.find(Feedback.class, id);
    }

    @Override
    public Feedback findFeedbackByApplication(Application application) {
        TypedQuery<Feedback> query = entityManager.createQuery("SELECT f FROM Feedback f WHERE f.application = :application", Feedback.class);
        query.setParameter("application", application);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public void update(Feedback feedback) {
        entityManager.merge(feedback);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Feedback feedback = findFeedbackById(id);
        if (feedback != null) {
            entityManager.remove(feedback);
        }
    }
}
