package com.kaushikmanivannan.hireandseek.controller;

import com.kaushikmanivannan.hireandseek.dto.ApplicationDTO;
import com.kaushikmanivannan.hireandseek.model.Application;
import com.kaushikmanivannan.hireandseek.model.Candidate;
import com.kaushikmanivannan.hireandseek.model.JobListing;
import com.kaushikmanivannan.hireandseek.model.User;
import com.kaushikmanivannan.hireandseek.service.ApplicationService;
import com.kaushikmanivannan.hireandseek.service.CandidateService;
import com.kaushikmanivannan.hireandseek.service.JobListingService;
import com.kaushikmanivannan.hireandseek.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CandidateApplicationController {

    private final UserService userService;
    private final CandidateService candidateService;
    private final ApplicationService applicationService;
    private final JobListingService jobListingService;

    @Autowired
    public CandidateApplicationController(ApplicationService theApplicationService, JobListingService theJobListingService, UserService theUserService, CandidateService theCandidateService) {
        this.applicationService = theApplicationService;
        this.jobListingService = theJobListingService;
        this.userService = theUserService;
        this.candidateService = theCandidateService;
    }

    // Displays the application form for a specific job listing
    @GetMapping("/apply/{jobListingId}")
    public String showApplicationForm(@PathVariable Long jobListingId, Model model){
        // Retrieves job listing by ID
        JobListing jobListing = jobListingService.findJobListingById(jobListingId);
        // Adds the job listing and a new ApplicationDTO to the model
        model.addAttribute("jobListing", jobListing);
        model.addAttribute("jobListingId", jobListingId);
        model.addAttribute("applicationDTO", new ApplicationDTO());
        // Returns the view name for the application form
        return "application-form";
    }

    // Processes the submission of the application form
    @PostMapping("/apply/{jobListingId}")
    public String processApplicationForm(
            @PathVariable Long jobListingId,
            @ModelAttribute("applicationDTO") @Valid ApplicationDTO applicationDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Authentication auth) {

        if (bindingResult.hasErrors()) {
            // If there are validation errors, return to the form view
            return "application-form";
        }

        // Get the email from authentication object
        String email = auth.getName();

        try {
            applicationService.submitApplication(jobListingId, email, applicationDTO);
            // Add success message on successful application submission
            redirectAttributes.addFlashAttribute("applicationSuccess", "You have successfully applied for the job!");
        } catch (Exception e) {
            // Add error message if there is an exception during application submission
            redirectAttributes.addFlashAttribute("applicationError", "There was an error processing your application: " + e.getMessage());
        }

        // Redirect to the home page
        return "redirect:/home";
    }

    // Displays all applications associated with the logged-in user.
    @GetMapping("/my-applications")
    public String showMyApplications(Model model, Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        Candidate candidate = candidateService.findCandidateByUser(user);
        List<Application> applications = applicationService.findApplicationsByCandidate(candidate);
        // Adds applications to the model for display
        model.addAttribute("applications", applications);
        return "my-applications"; // Returns the view name for displaying applications
    }

    // Handles the withdrawal of an application
    @PostMapping("/withdraw-application/{applicationId}")
    public String withdrawApplication(@PathVariable Long applicationId, RedirectAttributes redirectAttributes) {
        try {
            Application application = applicationService.findApplicationById(applicationId);
            // Check if application is in 'Applied' state before allowing withdrawal.
            if (application != null && "Applied".equals(application.getApplicationStatus())) {
                applicationService.delete(application);
                // Success message for successful withdrawal
                redirectAttributes.addFlashAttribute("successMessage", "Application Successfully Withdrawn.");
            } else {
                // Error message for invalid application status
                redirectAttributes.addFlashAttribute("errorMessage", "Invalid application status.");
            }
        } catch (Exception e) {
            // Error message for exceptions encountered during withdrawal
            redirectAttributes.addFlashAttribute("errorMessage", "Error withdrawing application: " + e.getMessage());
        }
        // Redirect back to the applications page
        return "redirect:/my-applications";
    }
}