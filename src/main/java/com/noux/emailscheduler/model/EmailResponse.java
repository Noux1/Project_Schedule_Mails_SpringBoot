package com.noux.emailscheduler.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class EmailResponse {
    private boolean success;
    private String jobId;
    private String jobGroup;
    private String message;
}
