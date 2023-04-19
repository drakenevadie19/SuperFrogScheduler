package edu.tcu.cs.superfrogscheduler.user;

import edu.tcu.cs.superfrogscheduler.system.exception.ObjectAlreadyExistedException;
import edu.tcu.cs.superfrogscheduler.system.exception.ObjectNotFoundException;
import edu.tcu.cs.superfrogscheduler.user.security.UserSecurity;
import edu.tcu.cs.superfrogscheduler.user.security.UserSecurityRepository;
import edu.tcu.cs.superfrogscheduler.user.security.UserSecurityService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserSecurityRepository userSecurityRepository;

    @Mock
    UserSecurityService userSecurityService;

    @InjectMocks
    UserService userService;

    UserSecurity userSecurity;

    SuperFrogUser superFrogUser;

    List<SuperFrogUser> superFrogUsers;

    @BeforeEach
    void setUp() {

        userSecurity = new UserSecurity();
        superFrogUser = new SuperFrogUser();

        userSecurity.setId("123");
        userSecurity.setEmail("test@tcu.edu");
        userSecurity.setPassword("123456");
        userSecurity.setUser(superFrogUser);

        superFrogUser.setId("456");
        superFrogUser.setAddress("TCU CS");
        superFrogUser.setUserSecurity(userSecurity);
        superFrogUser.setEmail("test@tcu.edu");

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
    void createUserSuccess() {
        // Given
        given(this.userRepository.save(superFrogUser)).willReturn(superFrogUser);
        given(this.userRepository.findByEmail(Mockito.any(String.class))).willReturn(Optional.empty());

        // When
        SuperFrogUser savedSuperFrogUser = this.userService.createUser(superFrogUser);

        // Then
        assertThat(savedSuperFrogUser).isEqualTo(savedSuperFrogUser.getUserSecurity().getUser());
        assertThat(savedSuperFrogUser.getUserSecurity()).isEqualTo(savedSuperFrogUser.getUserSecurity());

        assertThat(savedSuperFrogUser.getFirstName()).isEqualTo(superFrogUser.getFirstName());
        assertThat(savedSuperFrogUser.getLastName()).isEqualTo(superFrogUser.getLastName());
        assertThat(savedSuperFrogUser.getPhoneNumber()).isEqualTo(superFrogUser.getPhoneNumber());
        assertThat(savedSuperFrogUser.getAddress()).isEqualTo(superFrogUser.getAddress());
        assertThat(savedSuperFrogUser.getEmail()).isEqualTo(superFrogUser.getEmail());

        verify(this.userRepository, times(1)).findByEmail(Mockito.any(String.class));
        verify(this.userRepository, times(1)).save(superFrogUser);
    }

    @Test
    void createUserEmailAlreadyExisted() {
        // Given
        given(this.userRepository.findByEmail(Mockito.any(String.class))).willReturn(Optional.of(superFrogUser));

        // When
        assertThrows(ObjectAlreadyExistedException.class, () -> this.userService.createUser(superFrogUser));

        // Then
        verify(this.userRepository, times(1)).findByEmail(Mockito.any(String.class));
        verify(this.userRepository, times(0)).save(superFrogUser);
    }

    @Test
    void findStudentByIdSuccess() {
        // Given
        given(this.userRepository.findById("456")).willReturn(Optional.of(superFrogUser));

        // When
        SuperFrogUser foundSuperFrogUser = this.userService.findStudentById("456");

        // Then
        assertThat(foundSuperFrogUser.getId()).isEqualTo(superFrogUser.getId());
        assertThat(foundSuperFrogUser.getFirstName()).isEqualTo(superFrogUser.getFirstName());
        assertThat(foundSuperFrogUser.getLastName()).isEqualTo(superFrogUser.getLastName());
        assertThat(foundSuperFrogUser.getPhoneNumber()).isEqualTo(superFrogUser.getPhoneNumber());
        assertThat(foundSuperFrogUser.getAddress()).isEqualTo(superFrogUser.getAddress());
        assertThat(foundSuperFrogUser.getEmail()).isEqualTo(superFrogUser.getEmail());

        verify(this.userRepository, times(1)).findById("456");

    }

    @Test
    void findStudentByIdNotFound() {
        // Given
        given(this.userRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty());

        // When
        assertThrows(ObjectNotFoundException.class, () -> this.userService.findStudentById("456"));

        // Then
        verify(this.userRepository, times(1)).findById("456");
    }
}