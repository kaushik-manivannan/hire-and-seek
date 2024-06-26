package com.kaushikmanivannan.hireandseek.controller;

import com.kaushikmanivannan.hireandseek.model.Application;
import com.kaushikmanivannan.hireandseek.model.JobListing;
import com.kaushikmanivannan.hireandseek.service.ApplicationService;
import com.kaushikmanivannan.hireandseek.service.EmailService;
import com.kaushikmanivannan.hireandseek.service.FeedbackService;
import com.kaushikmanivannan.hireandseek.service.JobListingService;
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
import java.util.Map;
import java.util.stream.Collectors;

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
                                           FeedbackService feedbackService) {
        this.jobListingService = jobListingService;
        this.applicationService = applicationService;
        this.emailService = emailService;
        this.feedbackService = feedbackService;
    }

    @GetMapping("/job-listings/{jobListingId}/applications")
    public String viewJobApplications(
            @PathVariable Long jobListingId,
            Model model) {

        // Retrieve the job listing by ID
        JobListing jobListing = jobListingService.findJobListingById(jobListingId);

        // Redirect to employer home if the job listing does not exist
        if (jobListing == null) {
            return "redirect:/employer-home";
        }

        // Fetch applications for the job listing and filter out rejected applications.
        List<Application> allApplications = applicationService.findApplicationsByJobListingId(jobListingId);
        List<Application> viewableApplications = allApplications.stream()
                .filter(app -> !app.getApplicationStatus().equals("Reject"))
                .toList();

        // Count applications by their statuses.
        Map<String, Long> statusCounts = allApplications.stream()
                .collect(Collectors.groupingBy(Application::getApplicationStatus, Collectors.counting()));

        // Ensure all statuses are initialized in the map to prevent null values.
        String[] statuses = {"Applied", "Accept", "Reject", "Shortlist"};
        for (String status : statuses) {
            statusCounts.putIfAbsent(status, 0L);
        }

        // Add model attributes for rendering in the view.
        model.addAttribute("jobListing", jobListing);
        model.addAttribute("jobApplications", viewableApplications);
        model.addAttribute("applicationsCount", viewableApplications.size());
        model.addAttribute("statusCounts", statusCounts);

        return "job-listing-applications"; // Return view for job listing applications
    }

    @PostMapping("/job-listings/{jobListingId}/applications/updateStatus/{applicationId}")
    public String updateApplicationStatus(
            @PathVariable Long applicationId,
            @PathVariable Long jobListingId,
            @RequestParam("status") String status,
            @RequestParam("feedback") String feedbackText,
            Model model,
            RedirectAttributes redirectAttributes) {

        // Retrieve the specific application by ID
        Application currentApplication = applicationService.findApplicationById(applicationId);
        model.addAttribute("currentApplication", currentApplication);

        // If feedback text is provided and not empty, save it
        if (feedbackText != null && !feedbackText.trim().isEmpty()) {
            feedbackService.saveFeedback(currentApplication, feedbackText);
            // Refresh the application details after updating feedback
            currentApplication = applicationService.findApplicationById(applicationId);
        }

        // Update the status of the application
        applicationService.updateStatus(currentApplication, status);

        // Send an email notification regarding the status update
        emailService.sendEmail(currentApplication, status, feedbackText);

        // Flash a success message for the status update.
        redirectAttributes.addFlashAttribute("message", "Application status updated successfully!");

        return "redirect:/job-listings/" + jobListingId + "/applications";
    }
}