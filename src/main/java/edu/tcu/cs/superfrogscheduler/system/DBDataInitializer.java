package edu.tcu.cs.superfrogscheduler.system;

import edu.tcu.cs.superfrogscheduler.request.Request;
import edu.tcu.cs.superfrogscheduler.request.RequestRepository;
import edu.tcu.cs.superfrogscheduler.request.RequestStatus;
import edu.tcu.cs.superfrogscheduler.user.entity.utils.PaymentPreference;
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
//        List<Request> requests = createRequests(50);

        this.userRepository.saveAll(users);
//        this.requestRepository.saveAll(requests);
    }


    private List<SuperFrogUser> createUsers(int totalUser) {
        List<SuperFrogUser> users = new ArrayList<>();

        for(int i = 0; i < totalUser; i++) {
            SuperFrogUser superFrogUser = new SuperFrogUser();
            superFrogUser.setAddress("TCU CS" + i);
            superFrogUser.setEmail("test" + i + "@tcu.edu");
            superFrogUser.setFirstName("firstName");
            superFrogUser.setLastName("lastName" + i);
            superFrogUser.setPaymentPreference(PaymentPreference.MAIL_CHECK);
            superFrogUser.setRequests(createRequestsWithDifferentStatus());
            superFrogUser.getRequests().forEach(request -> request.setSuperFrogUser(superFrogUser));
            UserSecurity.createUserSecurity(superFrogUser);

            users.add(superFrogUser);
        }

//        Collections.reverse(users);

        return users;
    }

    private List<Request> createRequestsWithDifferentStatus() {
        List<Request> requests = new ArrayList<>();

        Request request1 = new Request();
        request1.setEventTitle("RequestPending");
        request1.setRequestStatus(RequestStatus.PENDING);

        Request request2 = new Request();
        request2.setEventTitle("RequestApproved");
        request2.setRequestStatus(RequestStatus.APPROVED);

        Request request3 = new Request();
        request3.setEventTitle("RequestRejected");
        request3.setRequestStatus(RequestStatus.REJECTED);

        Request request4 = new Request();
        request4.setEventTitle("RequestCancelled");
        request4.setRequestStatus(RequestStatus.CANCELLED);

        Request request5 = new Request();
        request5.setEventTitle("RequestCompleted");
        request5.setRequestStatus(RequestStatus.COMPLETED);

        Request request6 = new Request();
        request6.setEventTitle("RequestIncomplete");
        request6.setRequestStatus(RequestStatus.INCOMPLETE);

        Request request7 = new Request();
        request7.setEventTitle("RequestSubmitted");
        request7.setRequestStatus(RequestStatus.SUBMITTED_TO_PAYROLL);

        Request request8 = new Request();
        request8.setEventTitle("RequestAssigned");
        request8.setRequestStatus(RequestStatus.ASSIGNED);

        requests.add(request1);
        requests.add(request2);
        requests.add(request3);
        requests.add(request4);
        requests.add(request5);
        requests.add(request6);
        requests.add(request7);
        requests.add(request8);

        for(int i = 0; i < requests.size(); i++) {
            Request request = requests.get(i);
            request.setEventTitle("Request "+i);
            request.setEventDate(LocalDate.parse("2023-12-10"));
            request.setEventDescription("Request "+i+" description");
            request.setCustomerFirstName("Customer "+i);
            request.setCustomerLastName("Lastname "+i);
            request.setCustomerPhoneNumber("1231221234");
            request.setCustomerEmail("Customer"+i+"lastname"+i+"@gmail.com");
        }

        return requests;
    }

    private List<Request> createRequests(int totalRequests) {
        List<Request> requests = new ArrayList<>();

        for(int i = 0; i < totalRequests; i++) {
            Request request = new Request();
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