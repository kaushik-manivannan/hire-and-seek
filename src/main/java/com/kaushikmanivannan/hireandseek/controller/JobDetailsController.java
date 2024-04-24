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
        JobListing job = jobListingService.findJobListingById(jobId);
        String email = auth.getName();
        User user = userService.findByEmail(email);
        boolean isCandidate = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CANDIDATE"));
        if(isCandidate){
            Candidate candidate = candidateService.findCandidateByUser(user);
            List<Application> applications = applicationService.findApplicationsByCandidate(candidate);
            Set<Long> appliedJobs = applications.stream()
                    .map(application -> application.getJobListing().getId())
                    .collect(Collectors.toSet());
            model.addAttribute("appliedJobs", appliedJobs);
        }
        model.addAttribute("job", job);
        return "job-details";
    }
}
