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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class JobListingController {

    private final JobListingService jobListingService;
    private final EmployerService employerService;
    private final UserService userService;

    public JobListingController(JobListingService theJobListingService, EmployerService theEmployerService, UserService theUserService){
        jobListingService = theJobListingService;
        employerService = theEmployerService;
        userService = theUserService;
    }

    @GetMapping("/post-new-job")
    public String showPostJobForm(Model model){
        model.addAttribute("jobListingDTO", new JobListingDTO());
        return "post-job-form";
    }

    @PostMapping("/post-new-job")
    public String processPostJobForm(@Valid @ModelAttribute("jobListingDTO") JobListingDTO jobListingDTO,
                                     BindingResult bindingResult,
                                     Model model,
                                     Authentication auth,
                                     RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            return "post-job-form";
        }

        String email = auth.getName();
        User user = userService.findByEmail(email);
        Employer employer = employerService.findEmployerByUser(user);
        jobListingService.createJobListing(jobListingDTO, employer);

        redirectAttributes.addFlashAttribute("jobs", employer.getJobListings());
        redirectAttributes.addFlashAttribute("message", "Job Posted Successfully!");
        return "redirect:/home";
    }

    @GetMapping("/edit-job/{id}")
    public String showEditJobForm(@PathVariable Long id, Model model) {
        JobListing jobListing = jobListingService.findJobListingById(id);
        model.addAttribute("jobListingDTO", jobListing);
        return "edit-job-form";
    }

    @PostMapping("/update-job/{id}")
    public String updateJobListing(@Valid @ModelAttribute("jobListingDTO") JobListingDTO jobListingDTO,
                                   BindingResult result, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "edit-job-form";
        }
        JobListing jobListing = jobListingService.findJobListingById(id);
        jobListing.setTitle(jobListingDTO.getTitle());
        jobListing.setDescription(jobListingDTO.getDescription());
        jobListing.setSalary(jobListingDTO.getSalary());
        jobListing.setLocation(jobListingDTO.getLocation());
        jobListingService.update(jobListing);
        redirectAttributes.addFlashAttribute("successMessage", "Job Listing Updated Successfully!");
        return "redirect:/home";
    }

    @GetMapping("/delete-job/{id}")
    public String deleteJobListing(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        JobListing jobListing = jobListingService.findJobListingById(id);
        jobListingService.delete(jobListing);
        redirectAttributes.addFlashAttribute("successMessage", "Job Listing Deleted Successfully!");
        return "redirect:/home";
    }
}
