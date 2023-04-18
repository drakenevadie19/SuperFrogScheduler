package edu.tcu.cs.superfrogscheduler.converter;

import edu.tcu.cs.superfrogscheduler.dto.RequestDto;
import edu.tcu.cs.superfrogscheduler.request.Request;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RequestDtoToRequestConverter implements Converter<RequestDto, Request> {

    @Override
    public Request convert(RequestDto source) {
        Request request = new Request();
        request.setId(source.id());
        request.setEventDate(source.eventDate());
        request.setEventTitle(source.eventTitle());
        request.setCustomerFirstName(source.customerFirstName());
        request.setCustomerLastName(source.customerLastName());
        request.setCustomerPhoneNumber(source.customerPhoneNumber());
        request.setCustomerEmail(source.customerEmail());
        request.setRequestStatus(source.requestStatus());
        request.setAssignedSuperFrogStudent(source.assignedSuperFrogStudent());
        return request;
    }

}
