package edu.tcu.cs.superfrogscheduler.user;

import edu.tcu.cs.superfrogscheduler.system.exception.ObjectAlreadyExistedException;
import edu.tcu.cs.superfrogscheduler.system.exception.ObjectNotFoundException;
import edu.tcu.cs.superfrogscheduler.user.entity.SuperFrogUser;
import edu.tcu.cs.superfrogscheduler.user.entity.utils.SuperFrogUserSpecification;
import edu.tcu.cs.superfrogscheduler.user.security.UserSecurityService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final UserSecurityService userSecurityService;

    public UserService(UserRepository userRepository, UserSecurityService userSecurityService) {
        this.userRepository = userRepository;
        this.userSecurityService = userSecurityService;
    }

    public Page<SuperFrogUser> findAllStudents(SuperFrogUserSpecification superFrogUserSpecification, Pageable format) {
        return this.userRepository.findAll(superFrogUserSpecification, format);
    }

    public SuperFrogUser findStudentById(String id) {
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

}
