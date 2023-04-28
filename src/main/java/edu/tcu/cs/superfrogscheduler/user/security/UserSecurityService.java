package edu.tcu.cs.superfrogscheduler.user.security;

import edu.tcu.cs.superfrogscheduler.user.entity.SuperFrogUser;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserSecurityService implements UserDetailsService {

    private final UserSecurityRepository userSecurityRepository;

    private final PasswordEncoder passwordEncoder;

    public UserSecurityService(UserSecurityRepository userSecurityRepository, PasswordEncoder passwordEncoder) {
        this.userSecurityRepository = userSecurityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserSecurity createUserSecurity(SuperFrogUser superFrogUser) {
        UserSecurity userSecurity = UserSecurity.createUserSecurity(superFrogUser);
        createBasicCredentials(userSecurity);

        return this.userSecurityRepository.save(userSecurity);
    }

    public void createBasicCredentials(UserSecurity userSecurity) {
        // default password will then be salted + hashed, so each user will have unique default password

        // will add this when have notification system
//        String defaultPassword = this.passwordEncoder.encode("randomPassword");
//        userSecurity.setPassword(this.passwordEncoder.encode(defaultPassword));

        // encode default text password before saving
        userSecurity.setPassword(this.passwordEncoder.encode("randomPassword"));
        userSecurity.setRoles("user");
        userSecurity.setEnabled(true);
    }

    public void deactivateUser(UserSecurity userSecurity) {
        userSecurity.setEnabled(false);
        this.userSecurityRepository.save(userSecurity);
    }

    // User will have email as username
    // loadUserbyUsername will be called by Spring authentication provider
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // First, we need to find this user from database.
        // If found, wrap the returned user instance in a UserPrincipal instance (to satisfy contract with spring security)
        // Otherwise, throw an exception.
        return this.userSecurityRepository.findByEmail(email)
                .map(UserPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException("user " + email + " is not found"));

    }
}
