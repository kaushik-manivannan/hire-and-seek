package com.kaushikmanivannan.hireandseek.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employer")
public class Employer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL)
    private List<JobListing> jobListings;

    public void addJobListing(JobListing newJobListing){

        if(jobListings == null){
            jobListings = new ArrayList<>();
        }

        jobListings.add(newJobListing);
        newJobListing.setEmployer(this);
    }

    public Employer() {
    }

    public Employer(User user, List<JobListing> jobListings) {
        this.user = user;
        this.jobListings = jobListings;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<JobListing> getJobListings() {
        return jobListings;
    }

    public void setJobListings(List<JobListing> jobListing) {
        this.jobListings = jobListing;
    }

    @Override
    public String toString() {
        return "Employer{" +
                "id=" + id +
                ", user=" + user +
                ", jobListings=" + jobListings +
                '}';
    }
}
