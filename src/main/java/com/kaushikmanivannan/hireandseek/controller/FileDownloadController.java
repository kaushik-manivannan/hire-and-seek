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
        Application application = applicationService.findApplicationById(applicationId);
        if (application == null || application.getResume() == null) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayResource resource = new ByteArrayResource(application.getResume());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + applicationId + "_resume.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(application.getResume().length)
                .body(resource);
    }
}
