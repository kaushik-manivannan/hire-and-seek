package com.kaushikmanivannan.hireandseek.controller;

import com.itextpdf.html2pdf.HtmlConverter;
import com.kaushikmanivannan.hireandseek.dto.ApplicationDTO;
import com.kaushikmanivannan.hireandseek.model.Application;
import com.kaushikmanivannan.hireandseek.model.Candidate;
import com.kaushikmanivannan.hireandseek.model.JobListing;
import com.kaushikmanivannan.hireandseek.model.User;
import com.kaushikmanivannan.hireandseek.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/apply/{jobListingId}")
    public String showApplicationForm(@PathVariable Long jobListingId,
                                      Authentication auth,
                                      Model model){
        JobListing jobListing = jobListingService.findJobListingById(jobListingId);
        model.addAttribute("jobListing", jobListing);
        model.addAttribute("jobListingId", jobListingId);
        model.addAttribute("applicationDTO", new ApplicationDTO());
        return "application-form";
    }

    @PostMapping("/apply/{jobListingId}")
    public String processApplicationForm(
            @PathVariable Long jobListingId,
            @ModelAttribute("applicationDTO") @Valid ApplicationDTO applicationDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Authentication auth) {

        if (bindingResult.hasErrors()) {
            return "application-form";
        }

        String email = auth.getName();

        try {
            applicationService.submitApplication(jobListingId, email, applicationDTO);
            redirectAttributes.addFlashAttribute("applicationSuccess", "You have successfully applied for the job!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("applicationError", "There was an error processing your application: " + e.getMessage());
        }

        return "redirect:/home";
    }

    @GetMapping("/my-applications")
    public String showMyApplications(Model model, Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        Candidate candidate = candidateService.findCandidateByUser(user);
        List<Application> applications = applicationService.findApplicationsByCandidate(candidate);
        model.addAttribute("applications", applications);
        return "my-applications";
    }

    @PostMapping("/withdraw-application/{applicationId}")
    public String withdrawApplication(@PathVariable Long applicationId, RedirectAttributes redirectAttributes) {
        try {
            Application application = applicationService.findApplicationById(applicationId);
            if (application != null && "Applied".equals(application.getApplicationStatus())) {
                applicationService.delete(application);
                redirectAttributes.addFlashAttribute("successMessage", "Application Successfully Withdrawn.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Invalid application status.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error withdrawing application: " + e.getMessage());
        }
        return "redirect:/my-applications";
    }

}
