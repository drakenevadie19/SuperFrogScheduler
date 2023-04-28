package edu.tcu.cs.superfrogscheduler.user.security;

import edu.tcu.cs.superfrogscheduler.system.exception.ObjectNotFoundException;
import edu.tcu.cs.superfrogscheduler.user.entity.SuperFrogUser;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserSecurityService {

    private final UserSecurityRepository userSecurityRepository;

    public UserSecurityService(UserSecurityRepository userSecurityRepository) {
        this.userSecurityRepository = userSecurityRepository;
    }

    public UserSecurity createUserSecurity(SuperFrogUser superFrogUser) {
        UserSecurity userSecurity = UserSecurity.createUserSecurity(superFrogUser);
        userSecurity.setPassword("123456");
        userSecurity.setRoles("user");
        userSecurity.setEnabled(true);
        //no need, createUserSecurity() will set up connections
//        superFrogUser.setUserSecurity(userSecurity);
//        userSecurity.setUser(superFrogUser);

        return this.userSecurityRepository.save(userSecurity);
    }

    public void deactivateUser(UserSecurity userSecurity) {
        userSecurity.setEnabled(false);
        this.userSecurityRepository.save(userSecurity);
    }
}
