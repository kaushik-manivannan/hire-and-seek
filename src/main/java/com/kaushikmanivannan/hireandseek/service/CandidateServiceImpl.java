package com.kaushikmanivannan.hireandseek.service;

import com.kaushikmanivannan.hireandseek.dao.CandidateDAO;
import com.kaushikmanivannan.hireandseek.model.Candidate;
import com.kaushikmanivannan.hireandseek.model.User;
import org.springframework.stereotype.Service;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateDAO candidateDAO;

    public CandidateServiceImpl(CandidateDAO theCandidateDAO){
        candidateDAO = theCandidateDAO;
    }

    @Override
    public void save(Candidate candidate) {
        candidateDAO.save(candidate);
    }

    @Override
    public Candidate findCandidateByUser(User user) {
        return candidateDAO.findCandidateByUser(user);
    }

    @Override
    public void update(Candidate candidate) {
        candidateDAO.update(candidate);
    }

    @Override
    public void delete(Candidate candidate) {
        candidateDAO.delete(candidate);
    }
}
