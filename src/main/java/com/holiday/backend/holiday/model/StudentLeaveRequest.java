package com.holiday.backend.holiday.model;

import lombok.Data;

@Data
public class StudentLeaveRequest {

    private String startDate;

    private String endDate;

    private String reason;
}
