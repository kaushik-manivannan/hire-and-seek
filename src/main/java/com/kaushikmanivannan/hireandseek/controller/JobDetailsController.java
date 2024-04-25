package com.kaushikmanivannan.hireandseek.controller;

import com.kaushikmanivannan.hireandseek.model.Application;
import com.kaushikmanivannan.hireandseek.model.Candidate;
import com.kaushikmanivannan.hireandseek.model.JobListing;
import com.kaushikmanivannan.hireandseek.model.User;
import com.kaushikmanivannan.hireandseek.service.ApplicationService;
import com.kaushikmanivannan.hireandseek.service.CandidateService;
import com.kaushikmanivannan.hireandseek.service.JobListingService;
import com.kaushikmanivannan.hireandseek.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class JobDetailsController {

    private final JobListingService jobListingService;
    private final UserService userService;
    private final CandidateService candidateService;
    private final ApplicationService applicationService;

    public JobDetailsController(JobListingService theJobListingService, UserService theUserService, CandidateService theCandidateService, ApplicationService theApplicationService){
        jobListingService = theJobListingService;
        userService = theUserService;
        candidateService = theCandidateService;
        applicationService = theApplicationService;
    }

    @GetMapping("/job-details/{jobId}")
    public String showJobDetails(@PathVariable Long jobId,
                                 Model model,
                                 Authentication auth) {

        // Fetch job listing using the provided job ID.
        JobListing job = jobListingService.findJobListingById(jobId);

        // Retrieve the current authenticated user's email.
        String email = auth.getName();
        User user = userService.findByEmail(email);

        // Check if the authenticated user has a candidate role.
        boolean isCandidate = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CANDIDATE"));

        if (isCandidate) {
            // If user is a candidate, fetch their details and applications.
            Candidate candidate = candidateService.findCandidateByUser(user);
            List<Application> applications = applicationService.findApplicationsByCandidate(candidate);

            // Collect IDs of jobs to which the candidate has applied.
            Set<Long> appliedJobs = applications.stream()
                    .map(application -> application.getJobListing().getId())
                    .collect(Collectors.toSet());

            // Add applied jobs to the model
            model.addAttribute("appliedJobs", appliedJobs);
        }

        // Add the job listing details to the model
        model.addAttribute("job", job);

        return "job-details"; // Return the job details view
    }
}