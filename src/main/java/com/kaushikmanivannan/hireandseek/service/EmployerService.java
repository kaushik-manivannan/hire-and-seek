package com.kaushikmanivannan.hireandseek.service;

import com.kaushikmanivannan.hireandseek.model.Employer;
import com.kaushikmanivannan.hireandseek.model.User;

public interface EmployerService {
    void save(Employer employer);
    Employer findEmployerByUser(User user);
    void update(Employer employer);
    void delete(Employer employer);
}
