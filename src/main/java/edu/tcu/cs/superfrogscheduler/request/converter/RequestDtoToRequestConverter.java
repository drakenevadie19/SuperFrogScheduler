package edu.tcu.cs.superfrogscheduler.request.converter;

import edu.tcu.cs.superfrogscheduler.request.RequestStatus;
import edu.tcu.cs.superfrogscheduler.request.dto.RequestDto;
import edu.tcu.cs.superfrogscheduler.request.Request;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RequestDtoToRequestConverter implements Converter<RequestDto, Request> {

    @Override
    public Request convert(RequestDto source) {
        Request request = new Request();
        request.setId(String.valueOf(source.id()));
        request.setEventDate(LocalDate.parse(source.eventDate()));
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