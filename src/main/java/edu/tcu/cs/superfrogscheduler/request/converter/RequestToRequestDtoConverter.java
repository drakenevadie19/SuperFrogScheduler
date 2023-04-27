package edu.tcu.cs.superfrogscheduler.request.converter;

import edu.tcu.cs.superfrogscheduler.request.dto.RequestDto;
import edu.tcu.cs.superfrogscheduler.request.Request;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RequestToRequestDtoConverter implements Converter<Request, RequestDto> {

    @Override
    public RequestDto convert(Request source) {
        RequestDto requestDto = new RequestDto(
                source.getId(),
                source.getEventType(),
                source.getAddress(),
                source.getEventDate().toString(),
                source.getStartTime().toString(),
                source.getEndTime().toString(),
                source.getMileage().toString(),
                source.getEventTitle(),
                source.getCustomerFirstName(),
                source.getCustomerLastName(),
                source.getCustomerPhoneNumber(),
                source.getCustomerEmail(),
                source.getAssignedSuperFrogStudent(),
                source.getEventDescription(),
                source.getRequestStatus()
        );
        return requestDto;
    }
}


