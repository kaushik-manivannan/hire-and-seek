package com.kaushikmanivannan.hireandseek.dao;

import com.kaushikmanivannan.hireandseek.model.User;

public interface UserDAO {
    void save(User user);
    User findByEmail(String email);
    User findByPhone(String phone);
    void update(User user);
}
