package com.kaushikmanivannan.hireandseek.service;

import com.kaushikmanivannan.hireandseek.dto.JobListingDTO;
import com.kaushikmanivannan.hireandseek.model.Employer;
import com.kaushikmanivannan.hireandseek.model.JobListing;

import java.time.LocalDateTime;
import java.util.List;

public interface JobListingService {
    void save(JobListing jobListing);
    JobListing findJobListingById(Long id);
    List<JobListing> findJobListingsByUserId(Long UserId);
    List<JobListing> findAll();
    List<JobListing> search(String searchPhrase);
    void update(JobListing jobListing);
    void delete(JobListing jobListing);
    void createJobListing(JobListingDTO jobListingDTO, Employer employer);
    JobListing populateJobListingDTO(JobListing jobListing, JobListingDTO jobListingDTO);
    void saveAll(List<JobListing> jobListings);
}