package edu.tcu.cs.superfrogscheduler.system;

import edu.tcu.cs.superfrogscheduler.user.security.UserSecurity;
import edu.tcu.cs.superfrogscheduler.user.security.UserSecurityRepository;
import edu.tcu.cs.superfrogscheduler.user.SuperFrogUser;
import edu.tcu.cs.superfrogscheduler.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBDataInitializer implements CommandLineRunner {

    private final UserSecurityRepository userSecurityRepository;

    private final UserRepository userRepository;

    public DBDataInitializer(UserSecurityRepository userSecurityRepository, UserRepository userRepository) {
        this.userSecurityRepository = userSecurityRepository;
        this.userRepository = userRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        UserSecurity userSecurity = new UserSecurity();
        SuperFrogUser superFrogUser = new SuperFrogUser();

        userSecurity.setEmail("test@tcu.edu");
        userSecurity.setPassword("123456");
        userSecurity.setUser(superFrogUser);

        superFrogUser.setAddress("TCU CS");
        superFrogUser.setUserSecurity(userSecurity);
        superFrogUser.setEmail("test@tcu.edu");

        this.userRepository.save(superFrogUser);
    }
}
