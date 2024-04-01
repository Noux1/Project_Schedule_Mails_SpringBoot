package com.noux.emailscheduler.service;

import com.noux.emailscheduler.model.EmailRequest;
import com.noux.emailscheduler.model.EmailResponse;
import com.noux.emailscheduler.quartz.job.EmailJob;
import jakarta.annotation.Resource;
import org.quartz.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@Service
public class EmailSchedulerService {
    @Resource
    private Scheduler scheduler;

    JobDetail buildJobDetail(EmailRequest emailRequest){
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("email" , emailRequest.getEmail());
        jobDataMap.put("subject" , emailRequest.getSubject());
        jobDataMap.put("body" , emailRequest.getBody());
        return JobBuilder.newJob(EmailJob.class)
                .withIdentity(UUID.randomUUID().toString())
                .withDescription("send Job email")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    Trigger buildTrigger(JobDetail jobDetail , ZonedDateTime startAt){
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .startAt(Date.from(startAt.toInstant()))
                .withDescription("Send email trigger")
                .withIdentity(jobDetail.getKey().getName() , jobDetail.getKey().getGroup())
                .build();
    }

     public ResponseEntity<?> scheduleEmail(EmailRequest emailRequest) {
        try {


            ZonedDateTime dateTime = ZonedDateTime.of(emailRequest.getDateTime(), emailRequest.getTimeZone());
            if (dateTime.isBefore(ZonedDateTime.now())) {

                EmailResponse response = new EmailResponse(false, null, null,
                        "date time must be before current time");
                return ResponseEntity.badRequest().body(response);

            }

            JobDetail jobdetail = buildJobDetail(emailRequest);
            Trigger trigger = buildTrigger(jobdetail, dateTime);
            scheduler.scheduleJob(jobdetail, trigger);
            EmailResponse response = new EmailResponse(true, jobdetail.getKey().getName(), jobdetail.getKey().getGroup(),
                    "email scheduled successfully");
            return ResponseEntity.ok(response);


        } catch (SchedulerException ex) {

            EmailResponse emailResponse = new EmailResponse(false , null , null, ex.getMessage());
            return ResponseEntity.internalServerError().body(emailResponse);

        }
    }
}
