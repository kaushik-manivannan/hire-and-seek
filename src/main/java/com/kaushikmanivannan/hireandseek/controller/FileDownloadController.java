package com.kaushikmanivannan.hireandseek.controller;

import com.kaushikmanivannan.hireandseek.model.Application;
import com.kaushikmanivannan.hireandseek.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FileDownloadController {

    private final ApplicationService applicationService;

    @Autowired
    public FileDownloadController(ApplicationService theApplicationService){
        applicationService = theApplicationService;
    }

    @GetMapping("/downloadResume/{applicationId}")
    public ResponseEntity<ByteArrayResource> downloadResume(@PathVariable Long applicationId) {
        // Retrieves the application by ID
        Application application = applicationService.findApplicationById(applicationId);
        // Checks if application is null or if the resume data is missing; returns HTTP 404 Not Found if true
        if (application == null || application.getResume() == null) {
            return ResponseEntity.notFound().build();
        }

        // Creates a new ByteArrayResource from the resume byte array
        ByteArrayResource resource = new ByteArrayResource(application.getResume());

        // Prepares the response entity with proper headers, media type, and content
        return ResponseEntity.ok()
                // Sets the filename in the content-disposition header
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "Resume_" + application.getCandidate().getUser().getFirstName() + ".pdf")
                // Specifies the content type as PDF
                .contentType(MediaType.APPLICATION_PDF)
                // Sets the content length
                .contentLength(application.getResume().length)
                // Includes the ByteArrayResource in the response body
                .body(resource);
    }
}