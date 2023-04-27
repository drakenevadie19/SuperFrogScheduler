package edu.tcu.cs.superfrogscheduler.request.converter;

import edu.tcu.cs.superfrogscheduler.reports.EventType;
import edu.tcu.cs.superfrogscheduler.request.RequestStatus;
import edu.tcu.cs.superfrogscheduler.request.dto.RequestDto;
import edu.tcu.cs.superfrogscheduler.request.Request;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class RequestDtoToRequestConverter implements Converter<RequestDto, Request> {

    @Override
    public Request convert(RequestDto source) {
        Request request = new Request();
        request.setId(String.valueOf(source.id()));
        request.setEventType(source.eventType());
        request.setAddress(source.address());
        request.setEventDate(LocalDate.parse(source.eventDate()));
        request.setStartTime(LocalTime.parse(source.startTime()));
        request.setEndTime(LocalTime.parse(source.endTime()));
        request.setMileage(Double.parseDouble(source.mileage()));
        request.setEventTitle(String.valueOf(source.eventTitle()));
        request.setCustomerFirstName(source.customerFirstName());
        request.setCustomerLastName(source.customerLastName());
        request.setCustomerPhoneNumber(source.customerPhoneNumber());
        request.setCustomerEmail(source.customerEmail());
        request.setAssignedSuperFrogStudent(String.valueOf(source.assignedSuperFrogStudent()));
        request.setEventDescription(String.valueOf(source.eventDescription()));
        request.setRequestStatus(source.status());

        return request;
    }

}