package com.noux.emailscheduler.controller;
import com.noux.emailscheduler.model.EmailRequest;
import com.noux.emailscheduler.service.EmailSchedulerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j

public class EmailSchedulerController {
    private final EmailSchedulerService emailService;

    public EmailSchedulerController(EmailSchedulerService emailService) {
        this.emailService = emailService;
    }
    //http://localhost:8080/schedule/email
    @PostMapping("/schedule/email")
    public ResponseEntity<?> ScheduleEmail(@Valid @RequestBody EmailRequest emailRequest) {
       return emailService.scheduleEmail(emailRequest);
    }

}
