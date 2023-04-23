package edu.tcu.cs.superfrogscheduler.user.converter;

import edu.tcu.cs.superfrogscheduler.user.entity.SuperFrogUser;
import edu.tcu.cs.superfrogscheduler.user.entity.utils.SuperFrogUserSpecification;
import edu.tcu.cs.superfrogscheduler.user.converter.dto_to_user.UserDtoToSuperFrogUser;
import edu.tcu.cs.superfrogscheduler.user.converter.dto_to_user.UserInfoDtoToSuperFrogUser;
import edu.tcu.cs.superfrogscheduler.user.converter.dto_to_specification.UserSearchDtoToSuperFrogSpecification;
import edu.tcu.cs.superfrogscheduler.user.converter.user_to_dto.SuperFrogUserToUserDto;
import edu.tcu.cs.superfrogscheduler.user.converter.user_to_dto.SuperFrogUserToUserInfoDto;
import edu.tcu.cs.superfrogscheduler.user.converter.user_to_dto.SuperFrogUserToUserWorkDetailsDto;
import edu.tcu.cs.superfrogscheduler.user.dto.UserDto;
import edu.tcu.cs.superfrogscheduler.user.dto.UserInfoDto;
import edu.tcu.cs.superfrogscheduler.user.dto.UserSearchDto;
import edu.tcu.cs.superfrogscheduler.user.dto.UserWorkDetailsDto;
import org.springframework.stereotype.Component;

@Component
public class Converter {

    private final UserDtoToSuperFrogUser userDtoToSuperFrogUser;

    private final SuperFrogUserToUserDto superFrogUserToUserDto;

    private final UserSearchDtoToSuperFrogSpecification userSearchDtoToSuperFrogSpecification;

    private final SuperFrogUserToUserWorkDetailsDto superFrogUserToUserWorkDetailsDto;

    private final UserInfoDtoToSuperFrogUser userInfoDtoToSuperFrogUser;

    private final SuperFrogUserToUserInfoDto superFrogUserToUserInfoDto;

    public Converter(UserDtoToSuperFrogUser userDtoToSuperFrogUser,
                     SuperFrogUserToUserDto superFrogUserToUserDto,
                     UserSearchDtoToSuperFrogSpecification userSearchDtoToSuperFrogSpecification,
                     SuperFrogUserToUserWorkDetailsDto superFrogUserToUserWorkDetailsDto,
                     UserInfoDtoToSuperFrogUser userInfoDtoToSuperFrogUser,
                     SuperFrogUserToUserInfoDto superFrogUserToUserInfoDto) {

        this.userDtoToSuperFrogUser = userDtoToSuperFrogUser;
        this.superFrogUserToUserDto = superFrogUserToUserDto;
        this.userSearchDtoToSuperFrogSpecification = userSearchDtoToSuperFrogSpecification;
        this.superFrogUserToUserWorkDetailsDto = superFrogUserToUserWorkDetailsDto;
        this.userInfoDtoToSuperFrogUser = userInfoDtoToSuperFrogUser;
        this.superFrogUserToUserInfoDto = superFrogUserToUserInfoDto;

    }

    public SuperFrogUser toSuperFrogUser(UserDto userDto) {
        return this.userDtoToSuperFrogUser.convert(userDto);
    }

    public SuperFrogUser toSuperFrogUser(UserInfoDto userInfoDto) {
        return this.userInfoDtoToSuperFrogUser.convert(userInfoDto);
    }

    public UserDto toUserDto(SuperFrogUser superFrogUser) {
        return this.superFrogUserToUserDto.convert(superFrogUser);
    }

    public UserInfoDto toUserInfoDto(SuperFrogUser superFrogUser) {
        return this.superFrogUserToUserInfoDto.convert(superFrogUser);
    }

    public UserWorkDetailsDto toUserWorkDetailsDto(SuperFrogUser superFrogUser) {
        return this.superFrogUserToUserWorkDetailsDto.convert(superFrogUser);
    }

    public SuperFrogUserSpecification toSuperFrogUserSpecification(UserSearchDto userSearchDto) {
        return this.userSearchDtoToSuperFrogSpecification.convert(userSearchDto);
    }



}
