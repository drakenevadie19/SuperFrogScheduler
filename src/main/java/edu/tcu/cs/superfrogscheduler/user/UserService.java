package edu.tcu.cs.superfrogscheduler.user;

import edu.tcu.cs.superfrogscheduler.system.exception.ObjectAlreadyExistedException;
import edu.tcu.cs.superfrogscheduler.user.security.UserSecurity;
import edu.tcu.cs.superfrogscheduler.user.security.UserSecurityService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final UserSecurityService userSecurityService;

    public UserService(UserRepository userRepository, UserSecurityService userSecurityService) {
        this.userRepository = userRepository;
        this.userSecurityService = userSecurityService;
    }

    public List findAllStudents() {
        return null;
    }

    public SuperFrogUser createUser(SuperFrogUser superFrogUser) {

        if(this.userRepository.findByEmail(superFrogUser.getEmail()).isPresent()) {
            throw new ObjectAlreadyExistedException("User", superFrogUser.getEmail());
        }
        // createUserSecurity will also create 2-way connection between user and userSecurity
        this.userSecurityService.createUserSecurity(superFrogUser);

        return this.userRepository.save(superFrogUser);
    }

}
