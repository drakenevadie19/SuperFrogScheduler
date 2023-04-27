package edu.tcu.cs.superfrogscheduler.request.dto;

import edu.tcu.cs.superfrogscheduler.reports.EventType;
import edu.tcu.cs.superfrogscheduler.request.RequestStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


//Dto - simplified version of the data that is used for displaying in a web page
public record RequestDto(
        String id,

        EventType eventType,

        String address,
        String eventDate,
        String startTime,
        String endTime,
        String mileage,
        String eventTitle,
        @NotEmpty(message = "Customer first name is required.")
        String customerFirstName,
        @NotEmpty(message = "Customer last name is required.")
        String customerLastName,
        @NotNull(message = "Customer phone number is required.")
        String customerPhoneNumber,
        @NotEmpty(message = "Customer email is required.")
        String customerEmail,
        String assignedSuperFrogStudent,
        String eventDescription,
        RequestStatus status) {
}


