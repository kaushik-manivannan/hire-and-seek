package com.kaushikmanivannan.hireandseek.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "job_listing")
public class JobListing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false, length = 3000)
    private String description;

    @Column(name = "salary", nullable = false)
    private Integer salary;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "employer_id", referencedColumnName = "id", nullable = false)
    private Employer employer;

    @OneToMany(mappedBy = "jobListing", cascade = CascadeType.ALL)
    private List<Application> applications;

    @Column(name = "date_posted", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime datePosted = LocalDateTime.now();

    public void addApplication(Application newApplication){
        if(applications == null){
            applications = new ArrayList<>();
        }

        applications.add(newApplication);
        newApplication.setJobListing(this);
    }

    public JobListing() {
    }

    public JobListing(String title, String description, Integer salary, String location, String companyName, Employer employer, LocalDateTime datePosted) {
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.location = location;
        this.companyName = companyName;
        this.employer = employer;
        this.datePosted = datePosted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(LocalDateTime datePosted) {
        this.datePosted = datePosted;
    }

    @Override
    public String toString() {
        return "JobListing{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", salary=" + salary +
                ", location='" + location + '\'' +
                ", companyName='" + companyName + '\'' +
                ", employer=" + employer +
                ", applications=" + applications +
                ", datePosted=" + datePosted +
                '}';
    }
}
