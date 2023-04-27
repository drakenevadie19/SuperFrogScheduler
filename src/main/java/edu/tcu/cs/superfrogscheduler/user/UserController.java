package edu.tcu.cs.superfrogscheduler.user;

import edu.tcu.cs.superfrogscheduler.system.utils.BaseSearchDtoToPagination;
import edu.tcu.cs.superfrogscheduler.system.utils.Pagination;
import edu.tcu.cs.superfrogscheduler.system.Result;
import edu.tcu.cs.superfrogscheduler.system.StatusCode;
import edu.tcu.cs.superfrogscheduler.user.converter.Converter;
import edu.tcu.cs.superfrogscheduler.user.dto.UserDto;
import edu.tcu.cs.superfrogscheduler.user.dto.UserInfoDto;
import edu.tcu.cs.superfrogscheduler.user.dto.UserSearchDto;
import edu.tcu.cs.superfrogscheduler.user.dto.UserWorkDetailsDto;
import edu.tcu.cs.superfrogscheduler.user.entity.SuperFrogUser;
import edu.tcu.cs.superfrogscheduler.user.entity.utils.SuperFrogUserSpecification;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("${api.endpoint.base-url}/users")
public class UserController {
    private final UserService userService;

    private final BaseSearchDtoToPagination baseSearchDtoToPagination;

    private final Converter converter;

    public UserController(UserService userService, BaseSearchDtoToPagination baseSearchDtoToPagination, Converter converter) {
        this.userService = userService;
        this.baseSearchDtoToPagination = baseSearchDtoToPagination;
        this.converter = converter;
    }

    // UC 15: Spirit Director finds SuperFrog Students
    @GetMapping
    // UserSearchDto extends the BaseDto store params like sortDirection, sortBy, email, etc
    public Result findAllUsers(@Valid UserSearchDto userSearchDto) {
        SuperFrogUserSpecification superFrogUserSpecification = converter.
                toSuperFrogUserSpecification(userSearchDto);

        Pagination pagination = this.baseSearchDtoToPagination.convert(userSearchDto);

        Page<SuperFrogUser> superFrogUsers = this.userService
                .findAllUsers(superFrogUserSpecification, pagination.format());

        List<UserDto> userDtos = superFrogUsers
                .stream()
                .map(converter::toUserDto)
                .toList();

        pagination.setResult(superFrogUsers.getTotalPages(), userDtos);

        return new Result(true, StatusCode.SUCCESS, "Find All Success", pagination);
    }

    // UC 16: Spirit Director views a SuperFrog Student account
    @GetMapping("/{id}")
    public Result findUserById(@PathVariable String id) {
        SuperFrogUser superFrogUser = this.userService.findUserById(id);

        UserWorkDetailsDto userWorkDetailsDto = this.converter.toUserWorkDetailsDto(superFrogUser);

        return new Result(true, StatusCode.SUCCESS, "Find One Success", userWorkDetailsDto);
    }


    // UC 13: Spirit Director creates account for a new SuperFrog Student
    @PostMapping
    public Result createAccount(@Valid @RequestBody UserDto userDto) {
        SuperFrogUser superFrogUser = this.converter.toSuperFrogUser(userDto);

        SuperFrogUser savedSuperFrogUser = this.userService.createUser(superFrogUser);

        UserDto savedUserDto = this.converter.toUserDto(savedSuperFrogUser);

        return new Result(true, StatusCode.SUCCESS, "Create user Success", savedUserDto);
    }

    // UC 20: SuperFrog Student edits profile information
    @PutMapping("/{id}")
    public Result updateUserById(@PathVariable String id, @Valid @RequestBody UserInfoDto userInfoDto) {;
        SuperFrogUser superFrogUser = this.converter.toSuperFrogUser(userInfoDto);

        SuperFrogUser updatedUser = this.userService.updateUserById(id, superFrogUser);

        UserInfoDto updatedUserInfoDto = this.converter.toUserInfoDto(updatedUser);

        return new Result(true, StatusCode.SUCCESS, "Update Success", updatedUserInfoDto);
    }

    // UC 14: Spirit Director deactivates the account of a SuperFrog Student
    // NOTE: cannot deactivate if there are assigned (not incomplete yet) or complete (not paid yet) requests
    @DeleteMapping("/{id}")
    public Result deactivateUserById(@PathVariable String id) {
        this.userService.deactivateUserById(id);

        return new Result(true, StatusCode.SUCCESS, "Deactivate Success");
    }
}
