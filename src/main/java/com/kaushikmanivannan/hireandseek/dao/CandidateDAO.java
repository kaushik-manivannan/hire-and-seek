package com.kaushikmanivannan.hireandseek.dao;

import com.kaushikmanivannan.hireandseek.model.Candidate;
import com.kaushikmanivannan.hireandseek.model.User;

public interface CandidateDAO {
    void save(Candidate candidate);
    Candidate findCandidateByUser(User user);
    void update(Candidate candidate);
    void delete(Candidate candidate);
}
