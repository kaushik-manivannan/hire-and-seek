package com.kaushikmanivannan.hireandseek.service;

import com.kaushikmanivannan.hireandseek.dao.JobListingDAO;
import com.kaushikmanivannan.hireandseek.dto.JobListingDTO;
import com.kaushikmanivannan.hireandseek.model.Employer;
import com.kaushikmanivannan.hireandseek.model.JobListing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobListingServiceImpl implements JobListingService {

    private final JobListingDAO jobListingDAO;

    @Autowired
    public JobListingServiceImpl(JobListingDAO theJobListingDAO){
        jobListingDAO = theJobListingDAO;
    }

    @Override
    public void save(JobListing jobListing) {
        jobListingDAO.save(jobListing);
    }

    @Override
    public JobListing findJobListingById(Long id) {
        return jobListingDAO.findJobListingById(id);
    }

    @Override
    public List<JobListing> findJobListingsByUserId(Long userId) {
        return jobListingDAO.findJobListingsByUserId(userId);
    }

    @Override
    public List<JobListing> findAll() {
        return jobListingDAO.findAll();
    }

    @Override
    public List<JobListing> search(String searchPhrase) {
        return jobListingDAO.search(searchPhrase);
    }

    @Override
    public void update(JobListing jobListing) {
        jobListingDAO.update(jobListing);
    }

    @Override
    public void delete(JobListing jobListing) {
        jobListingDAO.delete(jobListing);
    }

    @Override
    public void createJobListing(JobListingDTO jobListingDTO, Employer employer) {
        JobListing newJobListing = new JobListing();
        newJobListing.setTitle(jobListingDTO.getTitle());
        newJobListing.setDescription(jobListingDTO.getDescription());
        newJobListing.setSalary(jobListingDTO.getSalary());
        newJobListing.setLocation(jobListingDTO.getLocation());
        newJobListing.setCompanyName(jobListingDTO.getCompanyName());
        newJobListing.setEmployer(employer);
        newJobListing.setDatePosted(LocalDateTime.now());
        jobListingDAO.save(newJobListing);
    }
}
