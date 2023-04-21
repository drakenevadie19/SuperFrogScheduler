package edu.tcu.cs.superfrogscheduler.request.dto;

import edu.tcu.cs.superfrogscheduler.request.RequestStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Objects;


//Dto - simplified version of the data that is used for displaying in a web page
public record RequestDto(
        Long id,
        @NotNull(message = "Event date is required.")
        LocalDate eventDate,
        @NotEmpty(message = "Event title is required.")
        String eventTitle,
        @NotEmpty(message = "Customer first name is required.")
        String customerFirstName,
        @NotEmpty(message = "Customer last name is required.")
        String customerLastName,
        @NotNull(message = "Customer phone number is required.")
        Integer customerPhoneNumber,
        @NotEmpty(message = "Customer email is required.")
        String customerEmail,
        @NotNull(message = "Request status is required.")
        RequestStatus requestStatus,
        String assignedSuperFrogStudent
) {

}

