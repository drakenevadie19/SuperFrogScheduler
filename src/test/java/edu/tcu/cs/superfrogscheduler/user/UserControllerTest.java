package edu.tcu.cs.superfrogscheduler.user;


import edu.tcu.cs.superfrogscheduler.BaseControllerTests;
import edu.tcu.cs.superfrogscheduler.system.StatusCode;
import edu.tcu.cs.superfrogscheduler.system.exception.ObjectAlreadyExistedException;
import edu.tcu.cs.superfrogscheduler.system.exception.ObjectNotFoundException;
import edu.tcu.cs.superfrogscheduler.user.dto.UserDto;
import edu.tcu.cs.superfrogscheduler.user.security.UserSecurity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class UserControllerTest extends BaseControllerTests {

    @MockBean
    UserService userService;

    SuperFrogUser superFrogUser;

    UserDto userDto;

    List<SuperFrogUser> superFrogUsers;

    @BeforeEach
    void setUp() {
        superFrogUser = new SuperFrogUser();
        superFrogUser.setId("test_id");
        superFrogUser.setAddress("123 Main St, Suite 100, Fort Worth, TX, 76109");
        superFrogUser.setEmail("test@tcu.edu");
        superFrogUser.setPhoneNumber("(123) 456-7890");
        superFrogUser.setFirstName("John");
        superFrogUser.setLastName("Doe");
        UserSecurity.createUserSecurity(superFrogUser);

        userDto = new UserDto(null, "John", "Doe",
                superFrogUser.getPhoneNumber(), superFrogUser.getAddress(), superFrogUser.getEmail(), "user");

        superFrogUsers = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            SuperFrogUser superFrogUser = new SuperFrogUser();
            superFrogUser.setAddress("TCU CS" + i);
            superFrogUser.setEmail("test" + i + "@tcu.edu");
            superFrogUser.setFirstName("firstName");
            superFrogUser.setLastName("lastName" + i);
            UserSecurity.createUserSecurity(superFrogUser);

            superFrogUsers.add(superFrogUser);
        }
        superFrogUsers.sort(Comparator.comparing(SuperFrogUser::getLastName));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createAccountSuccess() throws Exception {
        // Given
        String json = this.objectMapper.writeValueAsString(userDto);
        given(this.userService.createUser(Mockito.any(SuperFrogUser.class))).willReturn(superFrogUser);

        // When and Then
        this.mockMvc.perform(post(this.baseUrl + "/users").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Create user Success"))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.firstName").value(superFrogUser.getFirstName()))
                .andExpect(jsonPath("$.data.lastName").value(superFrogUser.getLastName()))
                .andExpect(jsonPath("$.data.email").value(superFrogUser.getEmail()));

    }

    @Test
    void createAccountEmailAlreadyExisted() throws Exception {

        // Given
        String json = this.objectMapper.writeValueAsString(userDto);
        doThrow(new ObjectAlreadyExistedException("User", "test@tcu.edu")).when(this.userService).createUser(Mockito.any());

        // When and Then
        this.mockMvc.perform(post(this.baseUrl + "/users").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.CONFLICT))
                .andExpect(jsonPath("$.message").value("User test@tcu.edu is already existed!"));

    }

    @Test
    void findStudentByIdSuccess() throws Exception {
        // Given
        given(this.userService.findStudentById("test_id")).willReturn(superFrogUser);

        // When and then
        this.mockMvc.perform(get(this.baseUrl + "/users/test_id").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value(superFrogUser.getId()))
                .andExpect(jsonPath("$.data.email").value(superFrogUser.getEmail()))
                .andExpect(jsonPath("$.data.firstName").value(superFrogUser.getFirstName()))
                .andExpect(jsonPath("$.data.lastName").value(superFrogUser.getLastName()));

    }

    @Test
    void findStudentByIdNotFound() throws Exception {
        // Given
        doThrow(new ObjectNotFoundException("user", "123")).when(this.userService).findStudentById("123");

        // When and Then
        this.mockMvc.perform(get(this.baseUrl + "/users/123").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find user with Id 123 :("))
                .andExpect(jsonPath("$.data").isEmpty());

    }
}