package edu.tcu.cs.superfrogscheduler.user;

import edu.tcu.cs.superfrogscheduler.system.exception.ObjectAlreadyExistedException;
import edu.tcu.cs.superfrogscheduler.system.exception.ObjectNotFoundException;
import edu.tcu.cs.superfrogscheduler.user.security.UserSecurityService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


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

        if(this.userRepository.findByEmail(superFrogUser.getEmail()).isPresent()) {
            throw new ObjectAlreadyExistedException("user", superFrogUser.getEmail());
        }
        // createUserSecurity will also create 2-way connection between user and userSecurity
        this.userSecurityService.createUserSecurity(superFrogUser);

        return this.userRepository.save(superFrogUser);
    }

}
