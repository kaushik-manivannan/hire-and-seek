package com.kaushikmanivannan.hireandseek.dao;

import com.kaushikmanivannan.hireandseek.model.JobListing;

import java.util.List;

public interface JobListingDAO {
    void save(JobListing jobListing);
    JobListing findJobListingById(Long id);
    List<JobListing> findJobListingsByUserId(Long UserId);
    List<JobListing> findAll();
    List<JobListing> search(String searchPhrase);
    void update(JobListing jobListing);
    void delete(JobListing jobListing);
}
