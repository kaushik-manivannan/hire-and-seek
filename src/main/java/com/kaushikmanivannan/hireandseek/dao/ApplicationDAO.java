package com.kaushikmanivannan.hireandseek.dao;

import com.kaushikmanivannan.hireandseek.model.Application;
import com.kaushikmanivannan.hireandseek.model.JobListing;

import java.util.List;

public interface ApplicationDAO {
    void save(Application application);
    Application findApplicationById(Long id);
    List<Application> findApplicationsByCandidateId(Long candidateId);
    List<Application> findApplicationsByJobListingId(Long jobListingId);
    List<Application> search(String searchPhrase);
    void update(Application application);
    void delete(Application application);
}
