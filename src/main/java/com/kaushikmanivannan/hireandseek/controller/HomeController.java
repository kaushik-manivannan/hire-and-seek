package com.kaushikmanivannan.hireandseek.controller;

import com.kaushikmanivannan.hireandseek.model.Application;
import com.kaushikmanivannan.hireandseek.model.Candidate;
import com.kaushikmanivannan.hireandseek.model.JobListing;
import com.kaushikmanivannan.hireandseek.model.User;
import com.kaushikmanivannan.hireandseek.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    public HomeController(UserService theUserService, JobListingService theJobListingService, CandidateService theCandidateService, ApplicationService theApplicationService) {
        jobListingService = theJobListingService;
        userService = theUserService;
        candidateService = theCandidateService;
        applicationService = theApplicationService;
    }

    @GetMapping("/home")
    public String showHomePage(Model model, Authentication authentication,
                               @RequestParam(value = "searchPhrase", required = false) String searchPhrase) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        String role = authentication.getAuthorities().toString();

        List<JobListing> jobsToShow;
        if (searchPhrase != null && !searchPhrase.isBlank()) {
            // Perform the search for both candidates and employers
            jobsToShow = jobListingService.search("%" + searchPhrase + "%");
        } else {
            // No search phrase entered, show all jobs for candidates or jobs posted by the employer
            jobsToShow = role.contains("ROLE_CANDIDATE") ? jobListingService.findAll() : jobListingService.findJobListingsByUserId(user.getId());
        }

        // Reverse the list of jobs to show the newest first if they were not already ordered by date
        Collections.reverse(jobsToShow);

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

        model.addAttribute("searchPhrase", searchPhrase);
        return role.contains("ROLE_CANDIDATE") ? "candidate-home" : "employer-home";
    }
}

