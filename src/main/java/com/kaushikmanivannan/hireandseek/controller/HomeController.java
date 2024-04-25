package com.kaushikmanivannan.hireandseek.controller;

import com.kaushikmanivannan.hireandseek.model.Application;
import com.kaushikmanivannan.hireandseek.model.Candidate;
import com.kaushikmanivannan.hireandseek.model.JobListing;
import com.kaushikmanivannan.hireandseek.model.User;
import com.kaushikmanivannan.hireandseek.service.ApplicationService;
import com.kaushikmanivannan.hireandseek.service.CandidateService;
import com.kaushikmanivannan.hireandseek.service.JobListingService;
import com.kaushikmanivannan.hireandseek.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final JobListingService jobListingService;
    private final UserService userService;
    private final CandidateService candidateService;
    private final ApplicationService applicationService;

    public HomeController(UserService theUserService, JobListingService theJobListingService,
                          CandidateService theCandidateService, ApplicationService theApplicationService) {
        jobListingService = theJobListingService;
        userService = theUserService;
        candidateService = theCandidateService;
        applicationService = theApplicationService;
    }

    /**
     * Shows the home page with job listings.
     * For ROLE_CANDIDATE, all jobs are shown
     * For ROLE_EMPLOYER, only their posted jobs are shown
     */
    @GetMapping("/home")
    public String showHomePage(Model model, Authentication authentication,
                               @RequestParam(value = "searchPhrase", required = false) String searchPhrase) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        String role = authentication.getAuthorities().toString();

        // Search or display all jobs based on role
        List<JobListing> jobsToShow;
        if (searchPhrase != null && !searchPhrase.isBlank()) {
            jobsToShow = jobListingService.search("%" + searchPhrase + "%");
        } else {
            jobsToShow = role.contains("ROLE_CANDIDATE") ?
                    jobListingService.findAll() :
                    jobListingService.findJobListingsByUserId(user.getId());
        }

        // Reverse for newest first if unordered
        Collections.reverse(jobsToShow);

        // Differentiate between candidate and employer roles for displaying jobs
        if (role.contains("ROLE_CANDIDATE")) {
            Candidate candidate = candidateService.findCandidateByUser(user);
            List<Application> applications = applicationService.findApplicationsByCandidate(candidate);
            Set<Long> appliedJobs = applications.stream()
                    .map(application -> application.getJobListing().getId())
                    .collect(Collectors.toSet());
            model.addAttribute("jobs", jobsToShow);
            model.addAttribute("appliedJobs", appliedJobs);
        } else if (role.contains("ROLE_EMPLOYER")) {
            model.addAttribute("jobs", jobsToShow);
        }

        // Add the searchPhrase in the model
        model.addAttribute("searchPhrase", searchPhrase);

        // Return the view based on user role
        return role.contains("ROLE_CANDIDATE") ? "candidate-home" : "employer-home";
    }
}

