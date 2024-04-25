package com.kaushikmanivannan.hireandseek.service;

import com.kaushikmanivannan.hireandseek.model.Application;
import com.kaushikmanivannan.hireandseek.model.Feedback;
import jakarta.transaction.Transactional;

public interface FeedbackService {
    void saveFeedback(Application application, String feedbackText);
}
