package edu.tcu.cs.superfrogscheduler.user.dto;

import edu.tcu.cs.superfrogscheduler.request.dto.RequestDto;

import java.util.List;

public record UserWorkDetailsDto(
        String id,

        String roles,

        String firstName,

        String lastName,

        String phoneNumber,


        String address,

        String email,

        String personalSchedules,

        List<RequestDto> signedUpAppearances,

        List<RequestDto> completedAppearances
) {

}

