package edu.tcu.cs.superfrogscheduler.user.converter.dto_to_user;

import edu.tcu.cs.superfrogscheduler.user.entity.SuperFrogUser;
import edu.tcu.cs.superfrogscheduler.user.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToSuperFrogUser implements Converter<UserDto, SuperFrogUser> {

    @Override
    public SuperFrogUser convert(UserDto source) {
        SuperFrogUser superFrogUser = new SuperFrogUser();

        superFrogUser.setId(source.getId());
        superFrogUser.setFirstName(source.getFirstName());
        superFrogUser.setLastName(source.getLastName());
        superFrogUser.setEmail(source.getEmail());
        superFrogUser.setAddress(source.getAddress());
        superFrogUser.setPhoneNumber(source.getPhoneNumber());

        return superFrogUser;
    }
}
