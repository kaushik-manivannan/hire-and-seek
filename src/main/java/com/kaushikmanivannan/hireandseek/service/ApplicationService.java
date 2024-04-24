package com.kaushikmanivannan.hireandseek.service;

import com.kaushikmanivannan.hireandseek.dto.ApplicationDTO;
import com.kaushikmanivannan.hireandseek.model.Application;
import com.kaushikmanivannan.hireandseek.model.Candidate;

import java.util.List;

public interface ApplicationService {
    void save(Application application);
    List<Application> getApplicationsByJobListing(Long jobListingId);
    void updateApplicationStatus(Long applicationId, String status);
    Application findApplicationById(Long id);
    List<Application> findApplicationsByCandidate(Candidate candidate);
    List<Application> findApplicationsByJobListingId(Long jobListingId);
    List<Application> search(String searchPhrase);
    void update(Application application);
    void updateStatus(Application application, String status);
    void delete(Application application);
    void submitApplication(Long jobListingId, String email, ApplicationDTO applicationDTO);
}
