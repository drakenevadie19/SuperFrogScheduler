package edu.tcu.cs.superfrogscheduler.system;

import edu.tcu.cs.superfrogscheduler.request.Request;
import edu.tcu.cs.superfrogscheduler.request.RequestRepository;
import edu.tcu.cs.superfrogscheduler.request.RequestStatus;
import edu.tcu.cs.superfrogscheduler.user.UserService;
import edu.tcu.cs.superfrogscheduler.user.security.UserSecurity;
import edu.tcu.cs.superfrogscheduler.user.security.UserSecurityRepository;
import edu.tcu.cs.superfrogscheduler.user.entity.SuperFrogUser;
import edu.tcu.cs.superfrogscheduler.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DBDataInitializer implements CommandLineRunner {

    private final UserSecurityRepository userSecurityRepository;

    private final UserRepository userRepository;

    private final RequestRepository requestRepository;

    public DBDataInitializer(UserSecurityRepository userSecurityRepository, UserRepository userRepository, RequestRepository requestRepository) {
        this.userSecurityRepository = userSecurityRepository;
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        // create 50 default users
        List<SuperFrogUser> users = createUsers(50);
        List<Request> requests = createRequests(50);

        this.userRepository.saveAll(users);
        this.requestRepository.saveAll(requests);


        SuperFrogUser superFrogUser = new SuperFrogUser();
        superFrogUser.setFirstName("John");
        superFrogUser.setLastName("Doe");
        superFrogUser.setAddress("123 Main St.");
        superFrogUser.setEmail("johndoe@example.com");
        superFrogUser.setId("johndoe");


        UserSecurity userSecurity = new UserSecurity();
        userSecurity.setEmail("johndoe@example.com");
        userSecurity.setPassword("password");
        userSecurity.setRoles("user");
        userSecurity.setUser(superFrogUser);

        userSecurity.setUser(superFrogUser);
        superFrogUser.setUserSecurity(userSecurity);
        userRepository.save(superFrogUser);



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

    private List<Request> createRequests(int totalRequests) {
        List<Request> requests = new ArrayList<>();

        for(int i = 0; i < totalRequests; i++) {
            Request request = new Request();
            request.setId("100"+i);
            request.setEventTitle("Request "+i);
            request.setRequestStatus(RequestStatus.APPROVED);
            request.setEventDate(LocalDate.parse("2023-12-10"));
            request.setEventDescription("Request "+i+" description");
            request.setCustomerFirstName("Customer "+i);
            request.setCustomerLastName("Lastname "+i);
            request.setCustomerPhoneNumber("1231221234");
            request.setCustomerEmail("Customer"+i+"lastname"+i+"@gmail.com");

            requests.add(request);
        }

//        Collections.reverse(users);

        return requests;
    }
}