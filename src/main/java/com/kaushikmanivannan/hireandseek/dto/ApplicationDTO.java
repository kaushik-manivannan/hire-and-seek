package com.kaushikmanivannan.hireandseek.dto;

import com.kaushikmanivannan.hireandseek.validation.constraints.MultipartFileNotEmpty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;

public class ApplicationDTO {

    @Pattern(regexp = "^(https?://www\\.linkedin\\.com/.*)?$", message = "Please enter a valid LinkedIn URL")
    private String linkedInURL;

    @Pattern(regexp = "^(https?://.*|$)", message = "Please enter a valid Portfolio URL")
    private String portfolioURL;

    @NotNull(message = "Work Authorization Status is required")
    private Boolean workAuthorization;

    @MultipartFileNotEmpty(message = "Please provide a resume to apply for this job listing")
    private MultipartFile resume;

    public ApplicationDTO() {
    }

    public ApplicationDTO(String linkedInURL, String portfolioURL, Boolean workAuthorization, MultipartFile resume) {
        this.linkedInURL = linkedInURL;
        this.portfolioURL = portfolioURL;
        this.workAuthorization = workAuthorization;
        this.resume = resume;
    }

    public String getLinkedInURL() {
        return linkedInURL;
    }

    public void setLinkedInURL(String linkedInURL) {
        this.linkedInURL = linkedInURL;
    }

    public String getPortfolioURL() {
        return portfolioURL;
    }

    public void setPortfolioURL(String portfolioURL) {
        this.portfolioURL = portfolioURL;
    }

    public Boolean getWorkAuthorization() {
        return workAuthorization;
    }

    public void setWorkAuthorization(Boolean workAuthorization) {
        this.workAuthorization = workAuthorization;
    }

    public MultipartFile getResume() {
        return resume;
    }

    public void setResume(MultipartFile resume) {
        this.resume = resume;
    }
}
