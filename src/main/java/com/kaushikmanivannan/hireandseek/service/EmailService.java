package com.kaushikmanivannan.hireandseek.service;

import com.kaushikmanivannan.hireandseek.model.Application;

public interface EmailService {

    void sendEmail(
            Application application,
            String status,
            String feedbackText
    );
}
