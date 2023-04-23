package edu.tcu.cs.superfrogscheduler.system;

import edu.tcu.cs.superfrogscheduler.user.security.UserSecurity;
import edu.tcu.cs.superfrogscheduler.user.security.UserSecurityRepository;
import edu.tcu.cs.superfrogscheduler.user.entity.SuperFrogUser;
import edu.tcu.cs.superfrogscheduler.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
        // create 50 default users
        List<SuperFrogUser> users = createUsers(50);

        this.userRepository.saveAll(users);
    }


    private List<SuperFrogUser> createUsers(int totalUser) {
        List<SuperFrogUser> users = new ArrayList<>();

        for(int i = 0; i < totalUser; i++) {
            SuperFrogUser superFrogUser = new SuperFrogUser();
            superFrogUser.setAddress("TCU CS" + i);
            superFrogUser.setEmail("test" + i + "@tcu.edu");
            superFrogUser.setFirstName("firstName");
            superFrogUser.setLastName("lastName" + i);
            UserSecurity.createUserSecurity(superFrogUser);

            users.add(superFrogUser);
        }

//        Collections.reverse(users);

        return users;
    }
}
