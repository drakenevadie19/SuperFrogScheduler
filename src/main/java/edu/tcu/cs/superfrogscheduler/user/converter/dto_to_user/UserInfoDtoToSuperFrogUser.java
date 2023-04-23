package edu.tcu.cs.superfrogscheduler.user.converter.dto_to_user;

import edu.tcu.cs.superfrogscheduler.user.entity.SuperFrogUser;
import edu.tcu.cs.superfrogscheduler.user.dto.UserDto;
import edu.tcu.cs.superfrogscheduler.user.dto.UserInfoDto;
import org.springframework.stereotype.Component;

@Component
public class UserInfoDtoToSuperFrogUser extends UserDtoToSuperFrogUser {

    @Override
    public SuperFrogUser convert(UserDto source) {
        SuperFrogUser superFrogUser = super.convert(source);

        // Additional conversions specific to the child converter
        if(source instanceof UserInfoDto userInfoDto) {
            superFrogUser.setIsInternationalStudent(userInfoDto.getIsInternational());
            superFrogUser.setPaymentPreference(userInfoDto.getPaymentPreference());
        }

        return superFrogUser;
    }
}


