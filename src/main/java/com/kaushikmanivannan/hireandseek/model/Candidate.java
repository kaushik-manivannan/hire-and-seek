package com.kaushikmanivannan.hireandseek.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "candidate")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    private List<Application> applications;

    public void addApplication(Application newApplication){
        if(applications == null){
            applications = new ArrayList<>();
        }

        applications.add(newApplication);
        newApplication.setCandidate(this);
    }

    public Candidate() {
    }

    public Candidate(User user) {
        this.user = user;}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", user=" + user +
                '}';
    }
}
