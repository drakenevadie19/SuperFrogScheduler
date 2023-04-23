package edu.tcu.cs.superfrogscheduler.user.dto;

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

        List<?> signedUpAppearances,

        List<?> completedAppearances
) {

}

