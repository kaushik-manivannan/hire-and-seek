package com.kaushikmanivannan.hireandseek.service;

import com.kaushikmanivannan.hireandseek.dao.FeedbackDAO;
import com.kaushikmanivannan.hireandseek.model.Application;
import com.kaushikmanivannan.hireandseek.model.Feedback;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackDAO feedbackDAO;

    @Autowired
    public FeedbackServiceImpl(FeedbackDAO feedbackDAO){
        this.feedbackDAO = feedbackDAO;
    }

    @Override
    public Feedback findFeedbackByApplication(Application application) {
        return feedbackDAO.findFeedbackByApplication(application);
    }

    @Override
    @Transactional
    public void saveFeedback(Application application, String feedbackText) {
        Feedback feedback = feedbackDAO.findFeedbackByApplication(application);
        if (feedback == null) {
            feedback = new Feedback();
            feedback.setApplication(application);
            feedback.setFeedbackText(feedbackText);
            feedbackDAO.save(feedback);
        } else {
            feedback.setFeedbackText(feedbackText);
            feedbackDAO.update(feedback);
        }
    }
}
