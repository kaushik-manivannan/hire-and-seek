package com.kaushikmanivannan.hireandseek.dao;

import com.kaushikmanivannan.hireandseek.model.Application;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ApplicationDAOImpl implements ApplicationDAO {

    private final EntityManager entityManager;

    @Autowired
    public ApplicationDAOImpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }

    @Override
    @Transactional
    public void save(Application application) {
        entityManager.persist(application);
    }

    @Override
    public Application findApplicationById(Long id) {
        Application application = entityManager.find(Application.class, id);
        return application;
    }

    @Override
    public List<Application> findApplicationsByCandidateId(Long candidateId) {
        TypedQuery<Application> query = entityManager.createQuery(
                "SELECT a FROM Application a " +
                "JOIN FETCH a.candidate c " +
                "WHERE c.id = :candidateId",
                Application.class
        );
        query.setParameter("candidateId", candidateId);
        return query.getResultList();
    }

    @Override
    public List<Application> findApplicationsByJobListingId(Long jobListingId) {
        TypedQuery<Application> query = entityManager.createQuery(
                "SELECT a FROM Application a " +
                        "JOIN FETCH a.jobListing jl " +
                        "WHERE jl.id = :jobListingId",
                Application.class
        );
        query.setParameter("jobListingId", jobListingId);
        return query.getResultList();
    }


    @Override
    public List<Application> search(String searchPhrase) {
        TypedQuery<Application> query = entityManager.createQuery(
                "SELECT a FROM Application a " + "JOIN FETCH a.jobListing jl " + "JOIN FETCH jl.employer e " + "WHERE jl.title LIKE :searchPhrase OR e.companyName LIKE :searchPhrase OR a.applicationStatus LIKE :searchPhrase",
                Application.class
        );
        query.setParameter("searchPhrase", searchPhrase);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void update(Application application) {
        entityManager.merge(application);
    }

    @Override
    @Transactional
    public void delete(Application application) {
        entityManager.remove(application);
    }
}
