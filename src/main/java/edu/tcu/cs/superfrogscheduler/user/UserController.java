package edu.tcu.cs.superfrogscheduler.user;

import edu.tcu.cs.superfrogscheduler.system.util.BaseSearchDtoToPagination;
import edu.tcu.cs.superfrogscheduler.system.util.Pagination;
import edu.tcu.cs.superfrogscheduler.system.Result;
import edu.tcu.cs.superfrogscheduler.system.StatusCode;
import edu.tcu.cs.superfrogscheduler.user.converter.SuperFrogUserToUserDto;
import edu.tcu.cs.superfrogscheduler.user.converter.SuperFrogUserToUserWorkDetailsDto;
import edu.tcu.cs.superfrogscheduler.user.converter.UserDtoToSuperFrogUser;
import edu.tcu.cs.superfrogscheduler.user.converter.UserSearchDtoToSuperFrogSpecification;
import edu.tcu.cs.superfrogscheduler.user.dto.UserDto;
import edu.tcu.cs.superfrogscheduler.user.dto.UserSearchDto;
import edu.tcu.cs.superfrogscheduler.user.dto.UserWorkDetailsDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("${api.endpoint.base-url}/users")
public class UserController {
    private final UserService userService;

    private final UserDtoToSuperFrogUser userDtoToSuperFrogUser;

    private final SuperFrogUserToUserDto superFrogUserToUserDto;

    private final UserSearchDtoToSuperFrogSpecification userSearchDtoToSuperFrogSpecification;

    private final BaseSearchDtoToPagination baseSearchDtoToPagination;

    private final SuperFrogUserToUserWorkDetailsDto superFrogUserToUserWorkDetailsDto;

    public UserController(UserService userService, UserDtoToSuperFrogUser userDtoToSuperFrogUser, SuperFrogUserToUserDto superFrogUserToUserDto, UserSearchDtoToSuperFrogSpecification userSearchDtoToSuperFrogSpecification, BaseSearchDtoToPagination baseSearchDtoToPagination, SuperFrogUserToUserWorkDetailsDto superFrogUserToUserWorkDetailsDto) {
        this.userService = userService;
        this.userDtoToSuperFrogUser = userDtoToSuperFrogUser;
        this.superFrogUserToUserDto = superFrogUserToUserDto;
        this.userSearchDtoToSuperFrogSpecification = userSearchDtoToSuperFrogSpecification;
        this.baseSearchDtoToPagination = baseSearchDtoToPagination;
        this.superFrogUserToUserWorkDetailsDto = superFrogUserToUserWorkDetailsDto;
    }

    // UC 15
    @GetMapping
    // UserSearchDto extends the BaseDto store params like sortDirection, sortBy, email, etc
    public Result findAllStudents(@Valid UserSearchDto userSearchDto) {
        SuperFrogUserSpecification superFrogUserSpecification =
                userSearchDtoToSuperFrogSpecification.convert(userSearchDto);

        Pagination pagination = this.baseSearchDtoToPagination.convert(userSearchDto);

        Page<SuperFrogUser> superFrogUsers = this.userService
                .findAllStudents(superFrogUserSpecification, pagination.format());

        List<UserDto> userDtos = superFrogUsers
                .stream()
                .map(superFrogUserToUserDto::convert)
                .toList();

        pagination.setResult(superFrogUsers.getTotalPages(), userDtos);

        return new Result(true, StatusCode.SUCCESS, "Find All Success", pagination);
    }

    // UC 16: Spirit Director views a SuperFrog Student account
    @GetMapping("/{id}")
    public Result findStudentById(@PathVariable String id) {
        SuperFrogUser superFrogUser = this.userService.findStudentById(id);

        UserWorkDetailsDto userWorkDetailsDto = this.superFrogUserToUserWorkDetailsDto.convert(superFrogUser);

        return new Result(true, StatusCode.SUCCESS, "Find One Success", userWorkDetailsDto);
    }


    // UC 13
    @PostMapping
    public Result createAccount(@Valid @RequestBody UserDto userDto) {
        SuperFrogUser superFrogUser = userDtoToSuperFrogUser.convert(userDto);

        SuperFrogUser savedSuperFrogUser = this.userService.createUser(superFrogUser);

        UserDto savedUserDto = superFrogUserToUserDto.convert(savedSuperFrogUser);

        return new Result(true, StatusCode.SUCCESS, "Create user Success", savedUserDto);
    }
}
