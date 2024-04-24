package com.kaushikmanivannan.hireandseek.service;

import com.kaushikmanivannan.hireandseek.model.Candidate;
import com.kaushikmanivannan.hireandseek.model.User;

public interface CandidateService {
    void save(Candidate candidate);
    Candidate findCandidateByUser(User user);
    void update(Candidate candidate);
    void delete(Candidate candidate);
}
