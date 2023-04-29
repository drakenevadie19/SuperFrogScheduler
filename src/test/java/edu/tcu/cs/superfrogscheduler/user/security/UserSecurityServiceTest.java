package edu.tcu.cs.superfrogscheduler.user.security;

import edu.tcu.cs.superfrogscheduler.user.entity.SuperFrogUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserSecurityServiceTest {

    @Mock
    UserSecurityRepository userSecurityRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserSecurityService userSecurityService;

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
    void createUserSecuritySuccess() {

        // given
        UserSecurity userSecurity = new UserSecurity();
        SuperFrogUser superFrogUser = new SuperFrogUser();

        userSecurity.setId("123");
        userSecurity.setEmail("test@tcu.edu");
        userSecurity.setPassword("123456");
        userSecurity.setUser(superFrogUser);

        superFrogUser.setId("456");
        superFrogUser.setAddress("TCU CS");
        superFrogUser.setEmail("test@tcu.edu");
        superFrogUser.setUserSecurity(userSecurity);

        given(this.passwordEncoder.encode(Mockito.any(String.class))).willReturn("hashedRandomPassword");

        // when
        Pair<UserSecurity, String> userSecurityWithPassword = this.userSecurityService.createUserSecurity(superFrogUser);
        UserSecurity savedUserSecurity = userSecurityWithPassword.getFirst();
        String plainPassword = userSecurityWithPassword.getSecond();

        // then
        assertThat(savedUserSecurity.getPassword()).isNotEmpty();
        assertThat(savedUserSecurity.getUser()).isEqualTo(superFrogUser);
        assertThat(savedUserSecurity.getEmail()).isEqualTo(superFrogUser.getEmail());
        assertThat(plainPassword).isNotEqualTo(userSecurity.getPassword());

        // save will not be called by UserSecurityService
        // userSecurity will be automatically saved as SuperFrogUser entity use cascade merge
        verify(this.userSecurityRepository, times(0)).save(Mockito.any(UserSecurity.class));

    }

}