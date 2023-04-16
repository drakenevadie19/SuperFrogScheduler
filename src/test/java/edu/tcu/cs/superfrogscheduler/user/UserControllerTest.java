package edu.tcu.cs.superfrogscheduler.user;


import edu.tcu.cs.superfrogscheduler.BaseControllerTests;
import edu.tcu.cs.superfrogscheduler.system.StatusCode;
import edu.tcu.cs.superfrogscheduler.system.exception.ObjectAlreadyExistedException;
import edu.tcu.cs.superfrogscheduler.user.dto.UserDto;
import edu.tcu.cs.superfrogscheduler.user.security.UserSecurity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class UserControllerTest extends BaseControllerTests {

    @MockBean
    UserService userService;

    SuperFrogUser superFrogUser;

    UserDto userDto;

    @BeforeEach
    void setUp() {
        superFrogUser = new SuperFrogUser();
        superFrogUser.setId("test_id");
        superFrogUser.setAddress("tcu");
        superFrogUser.setEmail("test@tcu.edu");
        superFrogUser.setPhoneNumber("123");
        superFrogUser.setFirstName("John");
        superFrogUser.setLastName("Doe");
        UserSecurity.createUserSecurity(superFrogUser);

        userDto = new UserDto(null, "John", "Doe",
                "123", "tcu", "test@tcu.edu", "user");
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
}