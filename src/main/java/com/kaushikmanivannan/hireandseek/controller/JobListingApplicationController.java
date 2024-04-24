package com.kaushikmanivannan.hireandseek.controller;

import com.kaushikmanivannan.hireandseek.model.Application;
import com.kaushikmanivannan.hireandseek.model.JobListing;
import com.kaushikmanivannan.hireandseek.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class JobListingApplicationController {
    private final JobListingService jobListingService;
    private final ApplicationService applicationService;
    private final EmailService emailService;
    private final FeedbackService feedbackService;

    @Autowired
    public JobListingApplicationController(JobListingService jobListingService,
                                           ApplicationService applicationService,
                                           EmailService emailService,
                                           EmployerService employerService,
                                           FeedbackService feedbackService) {
        this.jobListingService = jobListingService;
        this.applicationService = applicationService;
        this.emailService = emailService;
        this.feedbackService = feedbackService;
    }

    @GetMapping("/job-listings/{jobListingId}/applications")
    public String viewJobApplications(@PathVariable Long jobListingId, Model model, Authentication auth) {
        JobListing jobListing = jobListingService.findJobListingById(jobListingId);
        if (jobListing == null) {
            return "redirect:/employer-home";
        }
        List<Application> applications = applicationService.findApplicationsByJobListingId(jobListingId);
        model.addAttribute("jobListing", jobListing);
        model.addAttribute("jobApplications", applications);
        model.addAttribute("applicationsCount", applications.size());
        return "job-listing-applications";
    }

    @PostMapping("/job-listings/{jobListingId}/applications/updateStatus/{applicationId}")
    public String updateApplicationStatus(@PathVariable Long applicationId,
                                          @PathVariable Long jobListingId,
                                          @RequestParam("status") String status,
                                          @RequestParam("feedback") String feedbackText,
                                          Model model,
                                          RedirectAttributes redirectAttributes,
                                          Authentication auth) {

        Application currentApplication = applicationService.findApplicationById(applicationId);
        model.addAttribute("currentApplication", currentApplication);

        if (feedbackText != null && !feedbackText.trim().isEmpty()) {
            feedbackService.saveFeedback(currentApplication, feedbackText);
            currentApplication = applicationService.findApplicationById(applicationId);
        }

        if ("Reject".equals(status)) {
            applicationService.delete(currentApplication);
            emailService.sendEmail(currentApplication, status, feedbackText);
            redirectAttributes.addFlashAttribute("message", "Application rejected successfully!");
        } else {
            applicationService.updateStatus(currentApplication, status);
            Application updatedApplication = applicationService.findApplicationById(applicationId);
            emailService.sendEmail(updatedApplication, status, feedbackText);
            redirectAttributes.addFlashAttribute("message", "Application Status updated successfully!");
        }

        return "redirect:/job-listings/" + jobListingId + "/applications";
    }
}
