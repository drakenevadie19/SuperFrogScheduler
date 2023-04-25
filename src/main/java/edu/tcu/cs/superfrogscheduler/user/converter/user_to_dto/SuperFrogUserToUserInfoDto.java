package edu.tcu.cs.superfrogscheduler.user.converter.user_to_dto;

import edu.tcu.cs.superfrogscheduler.user.entity.SuperFrogUser;
import edu.tcu.cs.superfrogscheduler.user.dto.UserInfoDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SuperFrogUserToUserInfoDto implements Converter<SuperFrogUser, UserInfoDto> {

    @Override
    public UserInfoDto convert(SuperFrogUser source) {

        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setId(source.getId());
        userInfoDto.setFirstName(source.getFirstName());
        userInfoDto.setLastName(source.getLastName());
        userInfoDto.setPhoneNumber(source.getPhoneNumber());
        userInfoDto.setAddress(source.getAddress());
        userInfoDto.setEmail(source.getEmail());
        userInfoDto.setIsInternational(source.getIsInternationalStudent());
        userInfoDto.setPaymentPreference(source.getPaymentPreference());

        return userInfoDto;
    }

}
