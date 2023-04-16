package edu.tcu.cs.superfrogscheduler.user.security;

import edu.tcu.cs.superfrogscheduler.user.SuperFrogUser;
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


}
