package edu.tcu.cs.superfrogscheduler.converter;

import edu.tcu.cs.superfrogscheduler.dto.RequestDto;
import edu.tcu.cs.superfrogscheduler.request.Request;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class RequestToRequestDtoConverter implements Converter<Request, RequestDto> {

    @Override
    public RequestDto convert(Request source) {
        RequestDto requestDto = new RequestDto(
                source.getId(),
                source.getEventDate(),
                source.getEventTitle(),
                source.getCustomerFirstName(),
                source.getCustomerLastName(),
                source.getCustomerPhoneNumber(),
                source.getCustomerEmail(),
                source.getRequestStatus(),
                source.getAssignedSuperFrogStudent()
        );
        return requestDto;
    }
}



