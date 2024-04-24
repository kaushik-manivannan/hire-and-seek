package com.kaushikmanivannan.hireandseek.service;

import com.kaushikmanivannan.hireandseek.dao.EmployerDAO;
import com.kaushikmanivannan.hireandseek.model.Employer;
import com.kaushikmanivannan.hireandseek.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployerServiceImpl implements EmployerService {

    private final EmployerDAO employerDAO;

    @Autowired
    public EmployerServiceImpl(EmployerDAO theEmployerDAO){
        this.employerDAO = theEmployerDAO;
    }

    @Override
    public void save(Employer employer) {
        employerDAO.save(employer);
    }

    @Override
    public Employer findEmployerByUser(User user) {
        return employerDAO.findEmployerByUser(user);
    }

    @Override
    public void update(Employer employer) {
        employerDAO.update(employer);
    }

    @Override
    public void delete(Employer employer) {
        employerDAO.delete(employer);
    }
}
