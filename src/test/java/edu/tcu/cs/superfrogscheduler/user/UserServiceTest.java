package edu.tcu.cs.superfrogscheduler.user;

import edu.tcu.cs.superfrogscheduler.system.exception.ObjectAlreadyExistedException;
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

import java.util.Optional;

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
}