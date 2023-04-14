package edu.tcu.cs.superfrogscheduler.system;

import edu.tcu.cs.superfrogscheduler.user.SuperFrogUser;
import edu.tcu.cs.superfrogscheduler.user.UserRepository;
import edu.tcu.cs.superfrogscheduler.user.user_details.UserDetails;
import edu.tcu.cs.superfrogscheduler.user.user_details.UserDetailsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBDataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    private final UserDetailsRepository userDetailsRepository;

    public DBDataInitializer(UserRepository userRepository, UserDetailsRepository userDetailsRepository) {
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        SuperFrogUser user = new SuperFrogUser();
        user.setEmail("test@tcu.edu");
        user.setPassword("123456");

        UserDetails userDetails = new UserDetails();
        userDetails.setAddress("TCU CS");
        userDetails.setAge(20);
        userDetails.setUser(user);
        userDetails.setEmail("test@tcu.edu");

        this.userRepository.save(user);
        this.userDetailsRepository.save(userDetails);
    }
}
