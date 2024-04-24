package com.kaushikmanivannan.hireandseek.dao;

import com.kaushikmanivannan.hireandseek.model.JobListing;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class JobListingDAOImpl implements JobListingDAO {

    private final EntityManager entityManager;

    @Autowired
    public JobListingDAOImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    @Transactional
    public void save(JobListing jobListing) {
        entityManager.persist(jobListing);
    }

    @Override
    public JobListing findJobListingById(Long id) {
        JobListing jobListing = entityManager.find(JobListing.class, id);
        return jobListing;
    }

    @Override
    public List<JobListing> findJobListingsByUserId(Long userId){
        TypedQuery<JobListing> query = entityManager.createQuery(
                "SELECT jl FROM JobListing jl " +
                        "JOIN FETCH jl.employer e " +
                        "JOIN FETCH e.user u " +
                        "WHERE u.id = :userId",
                JobListing.class
        );
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public List<JobListing> findAll() {
        TypedQuery<JobListing> query = entityManager.createQuery(
                "FROM JobListing",
                JobListing.class
        );
        return query.getResultList();
    }

    @Override
    public List<JobListing> search(String searchPhrase) {
        TypedQuery<JobListing> query = entityManager.createQuery(
                "SELECT jl FROM JobListing jl " + "JOIN FETCH jl.employer e " + "WHERE jl.title LIKE :searchPhrase OR jl.description LIKE :searchPhrase OR jl.location LIKE :searchPhrase OR jl.companyName LIKE :searchPhrase",
                JobListing.class
        );
        query.setParameter("searchPhrase", searchPhrase);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void update(JobListing jobListing) {
        entityManager.merge(jobListing);
    }

    @Override
    @Transactional
    public void delete(JobListing jobListing) {
        entityManager.remove(jobListing);
    }
}
