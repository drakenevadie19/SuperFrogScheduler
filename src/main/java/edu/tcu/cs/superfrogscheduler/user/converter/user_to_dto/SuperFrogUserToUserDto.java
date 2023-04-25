package edu.tcu.cs.superfrogscheduler.user.converter.user_to_dto;

import edu.tcu.cs.superfrogscheduler.user.entity.SuperFrogUser;
import edu.tcu.cs.superfrogscheduler.user.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SuperFrogUserToUserDto implements Converter<SuperFrogUser, UserDto> {

    @Override
    public UserDto convert(SuperFrogUser source) {
        UserDto userDto = new UserDto(source.getId(), source.getFirstName(), source.getLastName(), source.getPhoneNumber(),
                source.getAddress(), source.getEmail(), source.getUserSecurity().getRoles());

        return userDto;
    }

}
