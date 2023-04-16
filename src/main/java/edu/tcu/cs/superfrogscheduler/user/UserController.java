package edu.tcu.cs.superfrogscheduler.user;

import edu.tcu.cs.superfrogscheduler.system.Result;
import edu.tcu.cs.superfrogscheduler.system.StatusCode;
import edu.tcu.cs.superfrogscheduler.system.exception.ObjectAlreadyExistedException;
import edu.tcu.cs.superfrogscheduler.user.converter.SuperFrogUserToUserDto;
import edu.tcu.cs.superfrogscheduler.user.converter.UserDtoToSuperFrogUser;
import edu.tcu.cs.superfrogscheduler.user.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("${api.endpoint.base-url}/users")
public class UserController {
    private final UserService userService;

    private final UserDtoToSuperFrogUser userDtoToSuperFrogUser;

    private final SuperFrogUserToUserDto superFrogUserToUserDto;

    public UserController(UserService userService, UserDtoToSuperFrogUser userDtoToSuperFrogUser, SuperFrogUserToUserDto superFrogUserToUserDto) {
        this.userService = userService;
        this.userDtoToSuperFrogUser = userDtoToSuperFrogUser;
        this.superFrogUserToUserDto = superFrogUserToUserDto;
    }

    @GetMapping
    public Result findAllStudents() {
        return null;
    }

    @PostMapping
    public Result createAccount(@Valid @RequestBody UserDto userDto) {
        SuperFrogUser superFrogUser = userDtoToSuperFrogUser.convert(userDto);

        SuperFrogUser savedSuperFrogUser = this.userService.createUser(superFrogUser);

        UserDto savedUserDto = superFrogUserToUserDto.convert(savedSuperFrogUser);

        return new Result(true, StatusCode.SUCCESS, "Create user Success", savedUserDto);
    }
}
