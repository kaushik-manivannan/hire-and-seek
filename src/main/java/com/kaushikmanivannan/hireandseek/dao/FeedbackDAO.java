package com.kaushikmanivannan.hireandseek.dao;

import com.kaushikmanivannan.hireandseek.model.Application;
import com.kaushikmanivannan.hireandseek.model.Feedback;

public interface FeedbackDAO {
    void save(Feedback feedback);
    Feedback findFeedbackById(Long id);
    Feedback findFeedbackByApplication(Application application);
    void update(Feedback feedback);
    void delete(Long id);
}
