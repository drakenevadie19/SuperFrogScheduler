package edu.tcu.cs.superfrogscheduler.user.converter.user_to_dto;

import edu.tcu.cs.superfrogscheduler.request.RequestStatus;
import edu.tcu.cs.superfrogscheduler.request.converter.RequestToRequestDtoConverter;
import edu.tcu.cs.superfrogscheduler.request.dto.RequestDto;
import edu.tcu.cs.superfrogscheduler.user.entity.SuperFrogUser;
import edu.tcu.cs.superfrogscheduler.user.dto.UserWorkDetailsDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SuperFrogUserToUserWorkDetailsDto implements Converter<SuperFrogUser, UserWorkDetailsDto> {

    private final RequestToRequestDtoConverter requestToRequestDtoConverter;

    public SuperFrogUserToUserWorkDetailsDto(RequestToRequestDtoConverter requestToRequestDtoConverter) {
        this.requestToRequestDtoConverter = requestToRequestDtoConverter;
    }

    @Override
    public UserWorkDetailsDto convert(SuperFrogUser source) {
        // filter, keep only signed up and completed appearances, then group them as 2 lists
        Map<RequestStatus, List<RequestDto>> requestStatusListMap = source.getRequests().stream()
                .filter(request -> request.getRequestStatus() == RequestStatus.ASSIGNED || request.getRequestStatus() == RequestStatus.COMPLETED)
                .map(requestToRequestDtoConverter::convert)
                .collect(Collectors.groupingBy(requestDto -> requestDto.status()));

        // if use map (to convert to RequestDto), then cannot use group by as RequestDto is
        // currently a record, does not have get Status
        List<RequestDto> signedUpAppearances = requestStatusListMap.get(RequestStatus.ASSIGNED);
        List<RequestDto> completedAppearances = requestStatusListMap.get(RequestStatus.COMPLETED);

        return new UserWorkDetailsDto(source.getId(), source.getUserSecurity().getRoles(), source.getFirstName(), source.getLastName(), source.getPhoneNumber(),
                source.getAddress(), source.getEmail(),null, signedUpAppearances, completedAppearances);
    }

}
