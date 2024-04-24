package com.kaushikmanivannan.hireandseek.model;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "candidate_id", referencedColumnName = "id", nullable = false)
    private Candidate candidate;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "job_listing_id", referencedColumnName = "id", nullable = false)
    private JobListing jobListing;

    @Column(name = "application_status", nullable = false)
    private String applicationStatus;

    @Column(name = "linkedin_url")
    private String linkedInURL;

    @Column(name = "portfolio_url")
    private String portfolioURL;

    @Column(name = "work_authorization", nullable = false)
    private Boolean workAuthorization;

    @Lob
    @Column(name = "resume", nullable = false, columnDefinition = "MEDIUMBLOB")
    private byte[] resume;

    public Application() {
    }

    public Application(String applicationStatus, String linkedInURL, String portfolioURL, Boolean workAuthorization) {
        this.applicationStatus = applicationStatus;
        this.linkedInURL = linkedInURL;
        this.portfolioURL = portfolioURL;
        this.workAuthorization = workAuthorization;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public JobListing getJobListing() {
        return jobListing;
    }

    public void setJobListing(JobListing jobListing) {
        this.jobListing = jobListing;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
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

    public byte[] getResume() {
        return resume;
    }

    public void setResume(byte[] resume) {
        this.resume = resume;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", candidate=" + candidate +
                ", jobListing=" + jobListing +
                ", applicationStatus='" + applicationStatus + '\'' +
                ", linkedInURL='" + linkedInURL + '\'' +
                ", portfolioURL='" + portfolioURL + '\'' +
                ", workAuthorization=" + workAuthorization +
                ", resume=" + Arrays.toString(resume) +
                '}';
    }
}
