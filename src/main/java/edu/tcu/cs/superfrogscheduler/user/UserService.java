package edu.tcu.cs.superfrogscheduler.user;

import edu.tcu.cs.superfrogscheduler.request.Request;
import edu.tcu.cs.superfrogscheduler.request.RequestStatus;
import edu.tcu.cs.superfrogscheduler.system.exception.DeactivateUserException;
import edu.tcu.cs.superfrogscheduler.system.exception.ObjectAlreadyExistedException;
import edu.tcu.cs.superfrogscheduler.system.exception.ObjectNotFoundException;
import edu.tcu.cs.superfrogscheduler.user.entity.SuperFrogUser;
import edu.tcu.cs.superfrogscheduler.user.entity.utils.SuperFrogUserSpecification;
import edu.tcu.cs.superfrogscheduler.user.security.UserSecurity;
import edu.tcu.cs.superfrogscheduler.user.security.UserSecurityService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final UserSecurityService userSecurityService;

    public UserService(UserRepository userRepository, UserSecurityService userSecurityService) {
        this.userRepository = userRepository;
        this.userSecurityService = userSecurityService;
    }

    public Page<SuperFrogUser> findAllUsers(SuperFrogUserSpecification superFrogUserSpecification, Pageable format) {
        return this.userRepository.findAll(superFrogUserSpecification, format);
    }

    public SuperFrogUser findUserById(String id) {
        return this.userRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("user", id));
    }

    public SuperFrogUser createUser(SuperFrogUser superFrogUser) {

        if (this.userRepository.findByEmail(superFrogUser.getEmail()).isPresent()) {
            throw new ObjectAlreadyExistedException("user", superFrogUser.getEmail());
        }
        // createUserSecurity will also create 2-way connection between user and userSecurity
        this.userSecurityService.createUserSecurity(superFrogUser);

        return this.userRepository.save(superFrogUser);
    }

    public SuperFrogUser updateUserById(String id, SuperFrogUser superFrogUser) {

        SuperFrogUser currentUser = this.userRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("user", id));


        Optional<SuperFrogUser> existingUser = this.userRepository.findByEmail(superFrogUser.getEmail());

        if (existingUser.isPresent() && !existingUser.get().getId().equals(id)) {
            throw new ObjectAlreadyExistedException("user", superFrogUser.getEmail());
        }

        currentUser.setFirstName(superFrogUser.getFirstName());
        currentUser.setLastName(superFrogUser.getLastName());
        currentUser.setPhoneNumber(superFrogUser.getPhoneNumber());
        currentUser.setAddress(superFrogUser.getAddress());
        currentUser.setEmail(superFrogUser.getEmail());
        currentUser.setIsInternationalStudent(superFrogUser.getIsInternationalStudent());
        currentUser.setPaymentPreference(superFrogUser.getPaymentPreference());
        currentUser.getUserSecurity().setEmail(superFrogUser.getEmail());

        return this.userRepository.save(currentUser);
    }

    // NOTE: cannot deactivate if there are assigned (not incomplete yet) or complete (not paid yet) requests
    // SO CANNOT DEACTIVATE if the status is either ASSIGNED or COMPLETED
    public void deactivateUserById(String id) {
        SuperFrogUser superFrogUser = this.findUserById(id);

        Map<RequestStatus, Long> requestStatusMap = superFrogUser
                .getRequests().stream()
                .collect(Collectors.groupingBy(Request::getRequestStatus, Collectors.counting()));

        Long totalAssignedRequests = requestStatusMap.get(RequestStatus.ASSIGNED);
        Long totalCompletedRequests = requestStatusMap.get(RequestStatus.COMPLETED);

        if(totalAssignedRequests != null || totalCompletedRequests != null) {
            String error1 = totalAssignedRequests != null
                    ? totalAssignedRequests + " incomplete assigned appearance"
                    : "";

            String error2 = totalCompletedRequests != null
                    ? " and " + totalCompletedRequests + " not yet been submitted to payroll"
                    : "";

            throw new DeactivateUserException(error1 + error2);
        }

        this.userSecurityService.deactivateUser(superFrogUser.getUserSecurity());
    }

}
