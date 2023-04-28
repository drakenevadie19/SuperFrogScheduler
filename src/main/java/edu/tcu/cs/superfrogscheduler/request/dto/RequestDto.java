package edu.tcu.cs.superfrogscheduler.request.dto;

import edu.tcu.cs.superfrogscheduler.reports.dto.EventType;
import edu.tcu.cs.superfrogscheduler.request.RequestStatus;
import edu.tcu.cs.superfrogscheduler.user.dto.UserDto;
import edu.tcu.cs.superfrogscheduler.user.entity.SuperFrogUser;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


//Dto - simplified version of the data that is used for displaying in a web page
public record RequestDto(
        String id,

        EventType eventType,

        String address,
        String eventDate,
        @NotEmpty(message = "Start time required") //null time crashes
        String startTime,
        @NotEmpty(message = "End time required") //null time crashes
        String endTime,

        @NotEmpty(message = "Mileage required") //null mileage crashes
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
        UserDto assignedSuperFrogStudent,
        String eventDescription,
        RequestStatus status) {
}


