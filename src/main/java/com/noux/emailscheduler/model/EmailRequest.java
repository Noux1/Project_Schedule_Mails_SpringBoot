package com.noux.emailscheduler.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.ZoneId;


@Data @AllArgsConstructor @NoArgsConstructor
public class EmailRequest {
    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private  String subject;

    @NotEmpty
    private  String body;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    private ZoneId timeZone;
}
