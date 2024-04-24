package com.kaushikmanivannan.hireandseek.service;

import com.kaushikmanivannan.hireandseek.dao.ApplicationDAO;
import com.kaushikmanivannan.hireandseek.dto.ApplicationDTO;
import com.kaushikmanivannan.hireandseek.model.Application;
import com.kaushikmanivannan.hireandseek.model.Candidate;
import com.kaushikmanivannan.hireandseek.model.JobListing;
import com.kaushikmanivannan.hireandseek.model.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationDAO applicationDAO;
    private final UserService userService;
    private final CandidateService candidateService;
    private final JobListingService jobListingService;

    public ApplicationServiceImpl(ApplicationDAO theApplicationDAO, UserService theUserService, CandidateService theCandidateService, JobListingService theJobListingService){
        applicationDAO = theApplicationDAO;
        userService = theUserService;
        candidateService = theCandidateService;
        jobListingService = theJobListingService;
    }

    @Override
    public void save(Application application) {
        applicationDAO.save(application);
    }

    @Override
    public List<Application> getApplicationsByJobListing(Long jobListingId) {
        return null;
    }

    @Override
    public void updateApplicationStatus(Long applicationId, String status) {

    }

    @Override
    public Application findApplicationById(Long id) {
        return applicationDAO.findApplicationById(id);
    }

    @Override
    public List<Application> findApplicationsByCandidate(Candidate candidate) {
        Long candidateId = candidate.getId();
        return applicationDAO.findApplicationsByCandidateId(candidateId);
    }

    @Override
    public List<Application> findApplicationsByJobListingId(Long jobListingId) {
        return applicationDAO.findApplicationsByJobListingId(jobListingId);
    }

    @Override
    public List<Application> search(String searchPhrase) {
        return applicationDAO.search(searchPhrase);
    }

    @Override
    public void update(Application application) {
        applicationDAO.update(application);
    }

    @Override
    public void updateStatus(Application application, String status) {
        if (application != null) {
            application.setApplicationStatus(status);
            applicationDAO.save(application);
        }
    }

    @Override
    public void delete(Application application) {
        applicationDAO.delete(application);
    }

    @Override
    public void submitApplication(Long jobListingId, String email, ApplicationDTO applicationDTO) {

        User user = userService.findByEmail(email);
        Candidate candidate = candidateService.findCandidateByUser(user);
        JobListing jobListing = jobListingService.findJobListingById(jobListingId);

        Application newApplication = new Application();
        newApplication.setLinkedInURL(applicationDTO.getLinkedInURL());
        newApplication.setPortfolioURL(applicationDTO.getPortfolioURL());
        newApplication.setWorkAuthorization(applicationDTO.getWorkAuthorization());
        newApplication.setCandidate(candidate);
        newApplication.setApplicationStatus("Applied");
        newApplication.setJobListing(jobListing);

        try {
            byte[] resumeBytes = applicationDTO.getResume().getBytes(); // Get bytes from MultipartFile
            newApplication.setResume(resumeBytes); // Set the byte[] for the resume
        } catch (IOException e) {
            throw new RuntimeException("Failed to store resume data", e);
        }

        applicationDAO.save(newApplication);

    }
}
