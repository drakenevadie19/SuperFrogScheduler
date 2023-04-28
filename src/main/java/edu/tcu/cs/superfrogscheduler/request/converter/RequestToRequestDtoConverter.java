package edu.tcu.cs.superfrogscheduler.request.converter;

import edu.tcu.cs.superfrogscheduler.request.dto.RequestDto;
import edu.tcu.cs.superfrogscheduler.request.Request;
import edu.tcu.cs.superfrogscheduler.user.converter.user_to_dto.SuperFrogUserToUserDto;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

@Component
public class RequestToRequestDtoConverter implements Converter<Request, RequestDto> {

    private final SuperFrogUserToUserDto superFrogUserToUserDto;
    public RequestToRequestDtoConverter(SuperFrogUserToUserDto superFrogUserToUserDto) {
        this.superFrogUserToUserDto = superFrogUserToUserDto;
    }


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
                source.getAssignedSuperFrogStudent() != null ? this.superFrogUserToUserDto.convert(source.getSuperFrogUser()):null,   //this.converter.toUserDto(source.getAssignedSuperFrogStudent()):null)
                source.getEventDescription(),
                source.getRequestStatus()
        );
        return requestDto;
    }
}


