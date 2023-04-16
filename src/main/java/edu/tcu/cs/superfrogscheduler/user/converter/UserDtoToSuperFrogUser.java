package edu.tcu.cs.superfrogscheduler.user.converter;

import edu.tcu.cs.superfrogscheduler.user.SuperFrogUser;
import edu.tcu.cs.superfrogscheduler.user.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToSuperFrogUser implements Converter<UserDto, SuperFrogUser> {

    @Override
    public SuperFrogUser convert(UserDto source) {
        SuperFrogUser superFrogUser = new SuperFrogUser();

        superFrogUser.setId(source.id());
        superFrogUser.setFirstName(source.firstName());
        superFrogUser.setLastName(source.lastName());
        superFrogUser.setEmail(source.email());
        superFrogUser.setAddress(source.address());
        superFrogUser.setPhoneNumber(source.phoneNumber());

        return superFrogUser;
    }
}
