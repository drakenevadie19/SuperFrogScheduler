package edu.tcu.cs.superfrogscheduler.user.converter;

import edu.tcu.cs.superfrogscheduler.user.SuperFrogUser;
import edu.tcu.cs.superfrogscheduler.user.dto.UserWorkDetailsDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SuperFrogUserToUserWorkDetailsDto implements Converter<SuperFrogUser, UserWorkDetailsDto> {

    @Override
    public UserWorkDetailsDto convert(SuperFrogUser source) {

        // these 2 lists will be created from the appearances list in source
        // use group by from stream api
        // will be done when have appearance table
        List<?> signedUpAppearances = new ArrayList<>();

        List<?> completedAppearances = new ArrayList<>();


        return new UserWorkDetailsDto(source.getId(), source.getUserSecurity().getRoles(), source.getFirstName(), source.getLastName(), source.getPhoneNumber(),
                source.getAddress(), source.getEmail(),null, signedUpAppearances, completedAppearances);
    }

}
