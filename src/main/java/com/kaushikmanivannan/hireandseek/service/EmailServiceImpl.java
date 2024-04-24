package com.kaushikmanivannan.hireandseek.service;

import com.kaushikmanivannan.hireandseek.model.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(
            Application application,
            String status
    ) {
        String toEmail = application.getCandidate().getUser().getEmail();
        String applicantName = application.getCandidate().getUser().getFirstName() + " " + application.getCandidate().getUser().getLastName();
        String jobTitle = application.getJobListing().getTitle();
        String companyName = application.getJobListing().getCompanyName();
        String subject = String.format(
                "Application Status Update - %s at %s",
                jobTitle, companyName
        );
        String content = String.format(
                "Dear %s,\n\n" +
                        "The status of your application for the position of %s at %s has been updated to: %sed.\n\n" +
                        "Best regards,\n" +
                        "Hire & Seek Team",
                applicantName, jobTitle, companyName, status
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);
    }
}
