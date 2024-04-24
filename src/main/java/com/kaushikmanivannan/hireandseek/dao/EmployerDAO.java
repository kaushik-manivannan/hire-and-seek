package com.kaushikmanivannan.hireandseek.dao;

import com.kaushikmanivannan.hireandseek.model.Employer;
import com.kaushikmanivannan.hireandseek.model.User;

public interface EmployerDAO {
    void save(Employer employer);
    Employer findEmployerByUser(User user);
    void update(Employer employer);
    void delete(Employer employer);
}
