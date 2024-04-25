package com.kaushikmanivannan.hireandseek.controller;

import com.kaushikmanivannan.hireandseek.dto.JobListingDTO;
import com.kaushikmanivannan.hireandseek.model.Employer;
import com.kaushikmanivannan.hireandseek.model.JobListing;
import com.kaushikmanivannan.hireandseek.model.User;
import com.kaushikmanivannan.hireandseek.service.EmployerService;
import com.kaushikmanivannan.hireandseek.service.JobListingService;
import com.kaushikmanivannan.hireandseek.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class JobListingController {

    private final JobListingService jobListingService;
    private final EmployerService employerService;
    private final UserService userService;

    public JobListingController(
            JobListingService theJobListingService,
            EmployerService theEmployerService,
            UserService theUserService){
        jobListingService = theJobListingService;
        employerService = theEmployerService;
        userService = theUserService;
    }

    @GetMapping("/post-new-job")
    public String showPostJobForm(Model model){
        // Adds an empty JobListingDTO object to the model to capture form data
        model.addAttribute("jobListingDTO", new JobListingDTO());
        return "post-job-form";
    }

    @PostMapping("/post-new-job")
    public String processPostJobForm(@Valid @ModelAttribute("jobListingDTO") JobListingDTO jobListingDTO,
                                     BindingResult bindingResult,
                                     Authentication auth,
                                     RedirectAttributes redirectAttributes){

        // Checks if there are validation errors
        if(bindingResult.hasErrors()){
            // If errors, re-render the form
            return "post-job-form";
        }

        // Extracts email from authentication
        String email = auth.getName();
        // Retrieves user by email
        User user = userService.findByEmail(email);
        // Finds the employer associated with the user
        Employer employer = employerService.findEmployerByUser(user);
        // Saves the new job listing
        jobListingService.createJobListing(jobListingDTO, employer);

        // Using RedirectAttributes to pass attributes to the redirect target
        redirectAttributes.addFlashAttribute("jobs", employer.getJobListings());
        redirectAttributes.addFlashAttribute("message", "Job Posted Successfully!");
        // Redirects to the home page after successful posting
        return "redirect:/home";
    }

    @GetMapping("/edit-job/{id}")
    public String showEditJobForm(@PathVariable Long id, Model model) {
        // Retrieves the job listing by ID
        JobListing jobListing = jobListingService.findJobListingById(id);
        model.addAttribute("jobListingDTO", jobListing);
        // Returns the view for editing a job listing
        return "edit-job-form";
    }

    @PostMapping("/update-job/{id}")
    public String updateJobListing(@Valid @ModelAttribute("jobListingDTO") JobListingDTO jobListingDTO,
                                   BindingResult result,
                                   @PathVariable Long id,
                                   RedirectAttributes redirectAttributes) {

        // Checks for validation errors
        if (result.hasErrors()) {
            // If errors, re-render the form
            return "edit-job-form";
        }

        // Fetches the existing job listing
        JobListing jobListing = jobListingService.findJobListingById(id);
        // Updates job listing with the new details from the form
        JobListing newJobListing = jobListingService.populateJobListingDTO(jobListing, jobListingDTO);
        // Saves the updated job listing
        jobListingService.update(newJobListing);

        // Adds a success message to be displayed after redirection
        redirectAttributes.addFlashAttribute("successMessage", "Job Listing Updated Successfully!");
        // Redirects to the home page
        return "redirect:/home";
    }

    @GetMapping("/delete-job/{id}")
    public String deleteJobListing(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        // Retrieves the job listing to be deleted
        JobListing jobListing = jobListingService.findJobListingById(id);
        // Deletes the job listing
        jobListingService.delete(jobListing);
        // Adds a success message to be shown after redirection
        redirectAttributes.addFlashAttribute("successMessage", "Job Listing Deleted Successfully!");
        // Redirects to the home page after deletion
        return "redirect:/home";
    }
}